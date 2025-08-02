package com.distraction.gloopgate.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;
import com.distraction.gloopgate.LevelData;
import com.distraction.gloopgate.MoveTarget;
import com.distraction.gloopgate.entity.RepeatingBackground;

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

    private final MoveTarget titley;
    private final MoveTarget playHelpy;

    private final BitmapFont font;
    private int selection;

    private final TextureRegion arrow;
    private final MoveTarget difficultyTitley;
    private final MoveTarget difficultyy;
    private int difficultySelection;

    public TitleScreen(Context context) {
        super(context);

        ignoreInput = true;
        in = new Transition(context, Transition.Type.FLASH_IN, 0.5f, () -> ignoreInput = false);
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f);

        title = context.getImage("title");

        titley = new MoveTarget(Constants.HEIGHT + 1);
        titley.setTarget(17);

        playHelpy = new MoveTarget(-10);
        playHelpy.setTarget(13);

        difficultyTitley = new MoveTarget(Constants.HEIGHT + 10);
        difficultyy = new MoveTarget(-Constants.HEIGHT / 2f);

        arrow = context.getImage("arrow");

        font = context.getFont();
        font.setColor(Constants.BLACK);

        slimeBg = new RepeatingBackground(context.getImage("slimebg"));
        context.resetScore();
    }

    private void goToDifficulty() {
        state = State.TO_DIFFICULTY;
        titley.setTarget(Constants.HEIGHT + 1);
        playHelpy.setTarget(-10);
    }

    private void onDifficulty() {
        state = State.DIFFICULTY;
        difficultyTitley.setTarget(Constants.HEIGHT - 8, 0.7f);
        difficultyy.setTarget(8, 0.7f);
    }

    private void toMain() {
        state = State.FROM_DIFFICULTY;
        difficultyTitley.setTarget(Constants.HEIGHT + 10, 0.7f);
        difficultyy.setTarget(-Constants.HEIGHT / 2f, 0.7f);
    }

    private void onMain() {
        state = State.MAIN;
        titley.setTarget(17);
        playHelpy.setTarget(13);
    }

    private void play() {
        ignoreInput = true;
        out = new Transition(
            context,
            Transition.Type.CHECKERED_OUT,
            0.5f,
            () -> context.sm.replace(new PlayScreen(context, LevelData.Difficulty.from(difficultySelection), 1))
        );
        out.start();
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        if (state == State.MAIN && !titley.isActive() && !playHelpy.isActive()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) if (selection > 0) selection--;
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) if (selection < 1) selection++;
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                if (selection == 0) goToDifficulty();
            }
        }
        if (state == State.DIFFICULTY && !difficultyTitley.isActive() && !difficultyy.isActive()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) if (difficultySelection > 0) difficultySelection--;
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) if (difficultySelection < 2) difficultySelection++;
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
        playHelpy.update(dt);
        difficultyTitley.update(dt);
        difficultyy.update(dt);

        if (state == State.TO_DIFFICULTY && !titley.isActive() && !playHelpy.isActive()) {
            onDifficulty();
        }

        if (state == State.FROM_DIFFICULTY && !difficultyTitley.isActive() && !difficultyy.isActive()) {
            onMain();
        }

        time += dt;
        titleOffset = MathUtils.sin(time * 4);
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
            font.draw(sb, "Play", 6, playHelpy.value);
            font.draw(sb, "Help", 36, playHelpy.value);
            sb.setColor(Constants.BLACK);
            if (!playHelpy.isActive() && !titley.isActive()) {
                sb.draw(pixel, selection == 0 ? 5 : 35, playHelpy.value - 10, 22, 1);
            }
        }

        if (state == State.DIFFICULTY || state == State.FROM_DIFFICULTY) {
            font.draw(sb, "Difficulty", 10, difficultyTitley.value);
            font.draw(sb, LevelData.Difficulty.NORMAL.text, 20, difficultyy.value + 30);
            font.draw(sb, LevelData.Difficulty.HARD.text, 20, difficultyy.value + 20);
            font.draw(sb, LevelData.Difficulty.WEIRD.text, 20, difficultyy.value + 10);
            if (!difficultyTitley.isActive() && !difficultyy.isActive()) {
                sb.draw(arrow, 14, difficultyy.value + 25 - difficultySelection * 10);
            }
        }

        in.render(sb);
        out.render(sb);

        sb.end();
    }
}
