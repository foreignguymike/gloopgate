package com.distraction.gloopgate.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;
import com.distraction.gloopgate.LevelData;
import com.distraction.gloopgate.SlimeSpawner;
import com.distraction.gloopgate.entity.Background;
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

    private final LevelData levelData;

    private final List<List<Slime>> slimes;
    private final SlimeSpawner slimeSpawner;

    private final Valid valid;
    private final Counter counter;

    private final Background bg;

    private Message message;

    private State state = State.READY;

    private int validSlimeCount;
    private int slimeCount;

    public PlayScreen(Context context, int level) {
        super(context);

        levelData = LevelData.create(level);

        slimes = new ArrayList<>();
        for (int i = 0; i < MAX_LANES; i++) slimes.add(new ArrayList<>());
        slimeSpawner = new SlimeSpawner(this, MAX_LANES, levelData.slimeInterval, levelData.slimeBias, levelData.biasAmount, levelData.slimeCount);

        valid = new Valid(context, levelData.validType, levelData.slimeTypes);
        counter = new Counter(context);

        bg = new Background(context);

        message = new Message(context, new String[] { "Day " + level });
    }

    @Override
    public void onSpawn(Slime.Type type, int lane) {
        int y = 44 - lane * 4;
        slimes.get(lane).add(new Slime(context, type, y, levelData.speed));
        slimeCount++;
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (state == State.READY) {
                if (message != null) message = null;
                state = State.GO;
            } else if (state == State.GO) {
                counter.count();
            }
        }
    }

    @Override
    public void update(float dt) {
        if (state == State.READY) return;

        slimeSpawner.update(dt);

        for (List<Slime> lane : slimes) {
            for (int i = 0; i < lane.size(); i++) {
                Slime slime = lane.get(i);
                slime.update(dt);
                if (slime.remove) {
                    Slime removedSlime = lane.remove(i--);
                    if (valid.isValid(removedSlime.type)) validSlimeCount++;
                    slimeCount--;
                }
            }
        }

        if (state != State.END && slimeCount == 0 && slimeSpawner.isDone()) {
            int diff = validSlimeCount - counter.count;
            String diffType = diff < 0 ? " extra" : " missed";
            message = new Message(context, diff == 0 ? new String[] { "PERFECT!" } : new String[] {diff + diffType, ":(" });
            state = State.END;
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

        sb.end();
    }
}
