package com.distraction.gloopgate.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.gloopgate.Context;

public class Background extends Entity {

    private final TextureRegion bridge;
    private final TextureRegion rail;

    private float time;

    public Background(Context context) {
        bridge = context.getImage("bridge");
        rail = context.getImage("rail");
    }

    @Override
    public void update(float dt) {
        time += dt;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        sb.draw(bridge, 0, 0);
        sb.draw(rail, 0, 45);
    }

    public void renderForeground(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        sb.draw(rail, 0, 16);
    }

}
