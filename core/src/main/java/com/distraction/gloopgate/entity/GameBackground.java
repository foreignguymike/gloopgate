package com.distraction.gloopgate.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.gloopgate.Context;

public class GameBackground extends Entity {

    private final TextureRegion bg;
    private final TextureRegion rail;

    public GameBackground(Context context) {
        bg = context.getImage("playbg");
        rail = context.getImage("rail");
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        sb.draw(bg, 0, 0);
        sb.draw(rail, 0, 45);
    }

    public void renderForeground(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        sb.draw(rail, 0, 16);
    }

}
