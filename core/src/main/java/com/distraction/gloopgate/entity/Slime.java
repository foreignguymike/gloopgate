package com.distraction.gloopgate.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;
import com.distraction.gloopgate.Utils;

public class Slime extends Entity {

    public enum Type {
        LIGHT(Constants.LIGHT),
        DARK(Constants.DARK),
        RED(Constants.RED),
        GREEN(Constants.GREEN),
        BLUE(Constants.BLUE),
        YELLOW(Constants.YELLOW),
        PURPLE(Constants.PURPLE)
        ;

        final Color color;
        Type(Color color) {
            this.color = color;
        }

        final static Type[] types = Type.values();

        public static Type random() {
            return types[MathUtils.random(0, types.length - 1)];
        }
    }

    private final Type type;
    private final float baseline;

    private float time;

    private final TextureRegion outline;
    private final TextureRegion fill;

    public Slime(Context context, Type type, float baseline) {
        this.type = type;
        this.baseline = baseline;

        outline = context.getImage("slimeoutline");
        fill = context.getImage("slimefill");

        x = Constants.WIDTH + 10;
        y = baseline;
    }

    @Override
    public void update(float dt) {
        time += dt;
        x -= 10 * dt;

        y = baseline + Math.abs(5 * MathUtils.sin(time * 3));

        if (x < -10) remove = true;
    }

    @Override
    public void render(SpriteBatch sb) {
        int x = snap(this.x);
        int y = snap(this.y);

        sb.setColor(1, 1, 1, 1);
        Utils.drawCentered(sb, outline, x, y);
        sb.setColor(type.color);
        Utils.drawCentered(sb, fill, x, y);
    }
}
