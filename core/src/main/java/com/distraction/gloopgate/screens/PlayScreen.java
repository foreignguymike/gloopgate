package com.distraction.gloopgate.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;
import com.distraction.gloopgate.LevelData;
import com.distraction.gloopgate.SlimeSpawner;
import com.distraction.gloopgate.entity.Background;
import com.distraction.gloopgate.entity.Counter;
import com.distraction.gloopgate.entity.Slime;
import com.distraction.gloopgate.entity.Valid;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends Screen implements SlimeSpawner.SpawnListener {

    private static final int MAX_LANES = 6;

    private final LevelData levelData;

    private final List<List<Slime>> slimes;
    private final SlimeSpawner slimeSpawner;

    private final Valid valid;
    private final Counter counter;

    private final Background bg;

    public PlayScreen(Context context, int level) {
        super(context);

        levelData = LevelData.create(level);

        slimes = new ArrayList<>();
        for (int i = 0; i < MAX_LANES; i++) slimes.add(new ArrayList<>());
        slimeSpawner = new SlimeSpawner(this, MAX_LANES, levelData.slimeInterval, levelData.slimeBias, levelData.biasAmount, levelData.slimeCount);

        valid = new Valid(context, levelData.validType, levelData.slimeTypes);
        counter = new Counter(context);

        bg = new Background(context);
    }

    @Override
    public void onSpawn(Slime.Type type, int lane) {
        int y = 40 - lane * 4;
        slimes.get(lane).add(new Slime(context, type, y, levelData.speed));
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) counter.count();
    }

    @Override
    public void update(float dt) {
        slimeSpawner.update(dt);

        for (List<Slime> lane : slimes) {
            for (int i = 0; i < lane.size(); i++) {
                Slime slime = lane.get(i);
                slime.update(dt);
                if (slime.remove) lane.remove(i--);
            }
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

        sb.end();
    }
}
