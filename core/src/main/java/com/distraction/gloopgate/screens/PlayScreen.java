package com.distraction.gloopgate.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;
import com.distraction.gloopgate.SlimeSpawner;
import com.distraction.gloopgate.entity.Counter;
import com.distraction.gloopgate.entity.Slime;
import com.distraction.gloopgate.entity.Valid;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends Screen implements SlimeSpawner.SpawnListener {

    private static final int MAX_LANES = 6;
    private static final float SLIME_INTERVAL = 0.4f;

    private final List<List<Slime>> slimes;
    private final SlimeSpawner slimeSpawner;

    private final Valid valid;
    private final Counter counter;

    public PlayScreen(Context context) {
        super(context);

        slimes = new ArrayList<>();
        for (int i = 0; i < MAX_LANES; i++) slimes.add(new ArrayList<>());
        slimeSpawner = new SlimeSpawner(this, MAX_LANES, SLIME_INTERVAL);

        List<Slime.Type> slimeTypes = new ArrayList<>();
        slimeTypes.add(Slime.Type.BLUE);
        slimeTypes.add(Slime.Type.GREEN);
        valid = new Valid(context, Valid.Type.VALID, slimeTypes);
        counter = new Counter(context);
    }

    @Override
    public void onSpawn(int lane) {
        int y = 40 - lane * 4;
        slimes.get(lane).add(new Slime(context, Slime.Type.random(), y));
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
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(cam.combined);
        sb.setColor(Constants.BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);

        for (List<Slime> lane : slimes) {
            for (Slime slime : lane) slime.render(sb);
        }

        valid.render(sb);
        counter.render(sb);

        sb.end();
    }
}
