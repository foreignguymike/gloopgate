package com.distraction.gloopgate.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;
import com.distraction.gloopgate.LevelData;
import com.distraction.gloopgate.SlimeSpawner;
import com.distraction.gloopgate.entity.GameBackground;
import com.distraction.gloopgate.entity.Counter;
import com.distraction.gloopgate.entity.Message;
import com.distraction.gloopgate.entity.Slime;
import com.distraction.gloopgate.entity.Valid;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends Screen implements SlimeSpawner.SpawnListener {

    enum State {
        READY,
        GO,
        END
    }

    private static final int MAX_LANES = 7;

    private final LevelData.Difficulty difficulty;
    private final int level;
    private final LevelData levelData;

    private final List<List<Slime>> slimes;
    private final SlimeSpawner slimeSpawner;

    private final Valid valid;
    private final Counter counter;

    private final GameBackground bg;

    private Message message;

    private State state = State.READY;

    private int validSlimeCount;
    private int currentSlimesOnScreen;

    public PlayScreen(Context context, LevelData.Difficulty difficulty, int level) {
        super(context);

        ignoreInput = true;
        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f);

        this.difficulty = difficulty;
        this.level = level;
        levelData = LevelData.create(difficulty, level);

        slimes = new ArrayList<>();
        for (int i = 0; i < MAX_LANES; i++) slimes.add(new ArrayList<>());
        slimeSpawner = new SlimeSpawner(this, MAX_LANES, levelData.slimeInterval, levelData.slimeBias, levelData.biasAmount, levelData.slimeCount);

        valid = new Valid(context, levelData.validType, levelData.slimeTypes);
        counter = new Counter(context);

        bg = new GameBackground(context);

        message = new Message(context, new String[]{"Day " + level});

        context.audio.playMusic("bg", 0.2f, true);
    }

    private void end() {
        ignoreInput = true;
        context.addScore(20 - Math.abs(counter.count - validSlimeCount));
        out = new Transition(
            context,
            Transition.Type.CHECKERED_OUT,
            0.5f,
            () -> {
                if (level < 5) context.sm.replace(new PlayScreen(context, difficulty, level + 1));
                else context.sm.replace(new ResultScreen(context, difficulty, context.score));
            }
        );
        out.start();
        context.audio.playSound("select");
    }

    @Override
    public void onSpawn(Slime.Type type, int lane) {
        int y = 44 - lane * 4;
        slimes.get(lane).add(new Slime(context, type, y, levelData.speed));
        currentSlimesOnScreen++;
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (state == State.READY) {
                if (message != null) message = null;
                state = State.GO;
            } else if (state == State.GO) {
                counter.count();
                context.audio.playSound("tick");
            } else if (state == State.END) {
                end();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            out = new Transition(context, Transition.Type.FLASH_OUT, 0.5f, () -> context.sm.replace(new TitleScreen(context)));
            out.start();
        }
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);

        if (state == State.READY) return;

        slimeSpawner.update(dt);

        for (List<Slime> lane : slimes) {
            for (int i = 0; i < lane.size(); i++) {
                Slime slime = lane.get(i);
                slime.update(dt);
                if (slime.remove) {
                    Slime removedSlime = lane.remove(i--);
                    if (valid.isValid(removedSlime.type)) validSlimeCount++;
                    currentSlimesOnScreen--;
                }
            }
        }

        if (state != State.END && currentSlimesOnScreen == 0 && slimeSpawner.isDone()) {
            int diff = validSlimeCount - counter.count;
            String diffType = diff < 0 ? " extra" : " missed";
            message = new Message(context, diff == 0 ? new String[]{"PERFECT!!"} : new String[]{Math.abs(diff) + diffType, ":("});
            state = State.END;
            if (diff == 0) context.audio.playSound("cheer");
            context.audio.playSound("finish");
        }

        valid.update(dt);
        counter.update(dt);
        bg.update(dt);
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(cam.combined);
        sb.setColor(Constants.GRASS);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        sb.setColor(Constants.SKY);
        sb.draw(pixel, 0, Constants.HEIGHT / 2f, Constants.WIDTH, Constants.HEIGHT / 2f);

        bg.render(sb);

        for (List<Slime> lane : slimes) {
            for (Slime slime : lane) slime.render(sb);
        }

        bg.renderForeground(sb);

        valid.render(sb);
        counter.render(sb);

        if (message != null) message.render(sb);

        in.render(sb);
        out.render(sb);

        sb.end();
    }
}
