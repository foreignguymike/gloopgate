package com.distraction.gloopgate.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;
import com.distraction.gloopgate.LevelData;
import com.distraction.gloopgate.MoveTarget;
import com.distraction.gloopgate.Utils;
import com.distraction.gloopgate.entity.Message;
import com.distraction.gloopgate.entity.RepeatingBackground;
import com.distraction.gloopgate.entity.TextEntity;

import java.util.List;

import de.golfgl.gdxgamesvcs.leaderboard.ILeaderBoardEntry;

public class TitleScreen extends Screen {

    enum State {
        MAIN,
        TO_DIFFICULTY,
        DIFFICULTY,
        FROM_DIFFICULTY
    }

    private State state = State.MAIN;

    private float time;

    private final RepeatingBackground slimeBg;

    private final TextureRegion title;
    private float titleOffset;

    private final BitmapFont font;

    private final Message difficultyTitle;
    private final TextEntity[] difficultyTexts;
    private final TextEntity[] scoreTexts;
    private final TextureRegion[] medals;

    private final MoveTarget titley;
    private final MoveTarget playy;

    private final MoveTarget difficultyTitley;
    private final MoveTarget pagex;
    private final MoveTarget pagey;

    private final TextureRegion arrow;
    private int arrowOffset;

    private final LevelData.Difficulty[] difficulties = LevelData.Difficulty.values();
    private int difficultySelection;

    public TitleScreen(Context context) {
        super(context);

        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        if (context.loaded) {
            ignoreInput = true;
            in.start();
        }
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f);

        title = context.getImage("title");

        titley = new MoveTarget(Constants.HEIGHT + 1);
        titley.setTarget(17);

        playy = new MoveTarget(-10);
        playy.setTarget(12);

        difficultyTitley = new MoveTarget(Constants.HEIGHT + 10);
        difficultyTitley.interpolation = Interpolation.smooth;
        pagex = new MoveTarget(0);
        pagex.interpolation = Interpolation.smooth;
        pagey = new MoveTarget(-Constants.HEIGHT);
        pagey.interpolation = Interpolation.smooth;

        arrow = context.getImage("arrow");

        font = context.getFont();

        difficultyTitle = new Message(context, new String[]{"Difficulty"});
        difficultyTexts = new TextEntity[difficulties.length];
        for (int i = 0; i < difficulties.length; i++) {
            difficultyTexts[i] = new TextEntity(font, difficulties[i].text, Constants.WIDTH / 2f + i * Constants.WIDTH, 50, TextEntity.Alignment.CENTER);
            difficultyTexts[i].setColor(Constants.BLACK);
        }

        BitmapFont smallFont = context.getSmallFont();
        scoreTexts = new TextEntity[difficulties.length * 3 * 2];
        for (int i = 0; i < scoreTexts.length; i += 6) {
            scoreTexts[i] = new TextEntity(smallFont, "-", 0, 0);
            scoreTexts[i + 1] = new TextEntity(smallFont, "-", 0, 0);
            scoreTexts[i + 2] = new TextEntity(smallFont, "-", 0, 0);
            scoreTexts[i + 3] = new TextEntity(smallFont, "-", 0, 0);
            scoreTexts[i + 4] = new TextEntity(smallFont, "-", 0, 0);
            scoreTexts[i + 5] = new TextEntity(smallFont, "-", 0, 0);
        }
        for (TextEntity te : scoreTexts) te.setColor(Constants.BLACK);

        medals = new TextureRegion[] {
            context.getImage("1"),
            context.getImage("2"),
            context.getImage("3")
        };

        slimeBg = new RepeatingBackground(context.getImage("slimebg"));
        context.resetScore();

        if (!context.leaderboardsInitialized && !context.leaderboardsRequesting) {
            context.fetchLeaderboard((success) -> {
                updateLeaderboards();
            });
        }

        context.audio.stopMusic();
        updateLeaderboards();
    }

    private void updateLeaderboards() {
        for (int i = 0; i < context.entries.size(); i++) {
            List<ILeaderBoardEntry> list = context.entries.get(i);
            for (int j = 0; j < list.size(); j++) {
                ILeaderBoardEntry entry = list.get(j);
                int k = i * 6 + j * 2;
                scoreTexts[k].setText(entry.getUserDisplayName());
                scoreTexts[k + 1].setText(entry.getFormattedValue());
            }
        }
    }

    private void goToDifficulty() {
        state = State.TO_DIFFICULTY;
        titley.setTarget(Constants.HEIGHT + 1);
        playy.setTarget(-10);
        context.audio.playSound("enter");
    }

    private void onDifficulty() {
        state = State.DIFFICULTY;
        difficultyTitley.setTarget(Constants.HEIGHT - 8, 0.3f);
        pagey.setTarget(8, 0.3f);
    }

    private void toMain() {
        state = State.FROM_DIFFICULTY;
        difficultyTitley.setTarget(Constants.HEIGHT + 10, 0.3f);
        pagey.setTarget(-Constants.HEIGHT, 0.3f);
    }

    private void onPageLeft() {
        if (difficultySelection > 0) {
            difficultySelection--;
            pagex.setTarget(-Constants.WIDTH * difficultySelection, 0.3f);
            context.audio.playSound("tick2");
        }
    }

    private void onPageRight() {
        if (difficultySelection < difficulties.length - 1) {
            difficultySelection++;
            pagex.setTarget(-Constants.WIDTH * difficultySelection, 0.3f);
            context.audio.playSound("tick2");
        }
    }

    private void onMain() {
        state = State.MAIN;
        titley.setTarget(17);
        playy.setTarget(13);
    }

    private void play() {
        ignoreInput = true;
        out = new Transition(
            context,
            Transition.Type.CHECKERED_OUT,
            0.5f,
            () -> context.sm.replace(new PlayScreen(context, difficulties[difficultySelection], 1))
        );
        out.start();
        context.audio.playSound("select");
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        if (state == State.MAIN && !titley.isActive() && !playy.isActive()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) goToDifficulty();
        }
        if (state == State.DIFFICULTY && !difficultyTitley.isActive() && !pagey.isActive()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) onPageLeft();
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) onPageRight();
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) play();
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) toMain();
        }
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);

        slimeBg.update(dt);

        titley.update(dt);
        playy.update(dt);
        difficultyTitley.update(dt);
        pagex.update(dt);
        pagey.update(dt);

        difficultyTitle.y = difficultyTitley.value - 5;
        difficultyTitle.update(dt);

        for (int i = 0; i < difficultyTexts.length; i++) {
            TextEntity te = difficultyTexts[i];
            te.x = pagex.value + Constants.WIDTH / 2f + i * Constants.WIDTH;
            te.y = pagey.value + 34;
        }

        for (int i = 0; i < scoreTexts.length; i += 6) {
            scoreTexts[i].x = pagex.value + Constants.WIDTH * MathUtils.round(i / 6f) + 20;
            scoreTexts[i].y = pagey.value + 24;
            scoreTexts[i + 1].x = scoreTexts[i].x + 25;
            scoreTexts[i + 1].y = scoreTexts[i].y;

            scoreTexts[i + 2].x = scoreTexts[i].x;
            scoreTexts[i + 2].y = scoreTexts[i].y - 10;
            scoreTexts[i + 3].x = scoreTexts[i + 1].x;
            scoreTexts[i + 3].y = scoreTexts[i + 2].y;

            scoreTexts[i + 4].x = scoreTexts[i].x;
            scoreTexts[i + 4].y = scoreTexts[i].y - 20;
            scoreTexts[i + 5].x = scoreTexts[i + 1].x;
            scoreTexts[i + 5].y = scoreTexts[i + 4].y;
        }

        if (state == State.TO_DIFFICULTY && !titley.isActive() && !playy.isActive()) {
            onDifficulty();
        }

        if (state == State.FROM_DIFFICULTY && !difficultyTitley.isActive() && !pagey.isActive()) {
            onMain();
        }

        time += dt;
        titleOffset = MathUtils.sin(time * 4);
        arrowOffset = time % 1 < 0.5f ? 0 : 1;
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(cam.combined);

        sb.setColor(Constants.TITLE_BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);

        sb.setColor(Constants.SLIME_BG);
        slimeBg.render(sb);

        sb.setColor(1, 1, 1, 1);
        sb.draw(title, 6, snap(titley.value + titleOffset));

        if (state == State.MAIN || state == State.TO_DIFFICULTY || state == State.FROM_DIFFICULTY) {
            font.setColor(Constants.BLACK);
            font.draw(sb, "Enter", 20, playy.value);
        }

        if (state == State.DIFFICULTY || state == State.FROM_DIFFICULTY) {
            difficultyTitle.render(sb);
            for (TextEntity te : difficultyTexts) te.render(sb);
            for (int i = 0; i < difficultyTexts.length; i++) {
                if (Math.abs(difficultySelection - i) > 1) continue;
                TextEntity te = difficultyTexts[i];
                if (i > 0) {
                    Utils.drawCenteredHFlip(sb, arrow, te.x - te.w / 2f - 6 + arrowOffset, te.y + 1);
                }
                if (i < difficulties.length - 1) {
                    Utils.drawCentered(sb, arrow, te.x + te.w / 2f + 6 - arrowOffset, te.y + 1);
                }
                sb.draw(medals[0], te.x - 24, te.y - 13);
                sb.draw(medals[1], te.x - 24, te.y - 23);
                sb.draw(medals[2], te.x - 24, te.y - 33);
            }
            for (int i = 0; i < scoreTexts.length; i++) {
                if (Math.abs(difficultySelection - (i / 6)) > 1) continue;
                scoreTexts[i].render(sb);
            }
        }

        in.render(sb);
        out.render(sb);

        sb.end();
    }
}
