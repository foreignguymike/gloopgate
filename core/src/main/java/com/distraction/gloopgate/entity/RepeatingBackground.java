package com.distraction.gloopgate.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RepeatingBackground extends Entity {

    private static final float SPEED = 8;

    private final TextureRegion image;

    public RepeatingBackground(TextureRegion image) {
        this.image = image;
    }

    @Override
    public void update(float dt) {
        x += SPEED * dt;
        y += SPEED * dt;
        if (x > 0) x -= 16;
        if (y > 0) y -= 16;
    }

    @Override
    public void render(SpriteBatch sb) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 0) continue;
                sb.draw(image, snap(x + col * 16), snap(y + row * 16));
            }
        }
    }
}
