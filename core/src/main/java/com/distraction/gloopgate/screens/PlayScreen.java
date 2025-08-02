package com.distraction.gloopgate.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;
import com.distraction.gloopgate.entity.Counter;
import com.distraction.gloopgate.entity.Slime;
import com.distraction.gloopgate.entity.Valid;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends Screen {

    private float slimeInterval = 2;
    private final List<Slime> slimes;

    private final Valid valid;
    private final Counter counter;

    public PlayScreen(Context context) {
        super(context);

        slimes = new ArrayList<>();
        slimes.add(new Slime(context, Slime.Type.random(), 30));

        List<Slime.Type> slimeTypes = new ArrayList<>();
        slimeTypes.add(Slime.Type.BLUE);
        slimeTypes.add(Slime.Type.GREEN);
        valid = new Valid(context, Valid.Type.VALID, slimeTypes);
        counter = new Counter(context);
    }

    private void addSlime() {
        slimes.add(new Slime(context, Slime.Type.random(), 30));
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) counter.count();
    }

    @Override
    public void update(float dt) {
        slimeInterval -= dt;
        if (slimeInterval < 0) {
            addSlime();
            slimeInterval = 2;
        }

        for (int i = 0; i < slimes.size(); i++) {
            Slime slime = slimes.get(i);
            slime.update(dt);
            if (slime.remove) slimes.remove(i--);
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

        for (Slime slime : slimes) slime.render(sb);

        valid.render(sb);
        counter.render(sb);

        sb.end();
    }
}
