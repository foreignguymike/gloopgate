package com.distraction.gloopgate.screens;

import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;
import com.distraction.gloopgate.entity.Slime;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends Screen {

    private float slimeInterval = 2;
    private final List<Slime> slimes;

    public PlayScreen(Context context) {
        super(context);

        slimes = new ArrayList<>();
        slimes.add(new Slime(context, Slime.Type.random(), 30));
    }

    private void addSlime() {
        slimes.add(new Slime(context, Slime.Type.random(), 30));
    }

    @Override
    public void input() {
        if (ignoreInput) return;
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

        System.out.println("slimes: " + slimes.size());
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(cam.combined);
        sb.setColor(Constants.BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);

        for (Slime slime : slimes) slime.render(sb);

        sb.end();
    }
}
