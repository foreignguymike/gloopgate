package com.distraction.gloopgate.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;
import com.distraction.gloopgate.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        public static List<Type> random(int amount) {
            List<Type> list = new ArrayList<>(Arrays.asList(types));
            while (list.size() > amount) list.remove(MathUtils.random(0, list.size() - 1));
            return list;
        }
    }

    public final Type type;

    public final float baseline;
    private final float speed;

    private float time;

    private final TextureRegion image;
    private final TextureRegion pixel;

    public Slime(Context context, Type type, float baseline, float speed) {
        this.type = type;
        this.baseline = baseline;
        this.speed = speed;

        image = context.getImage("slime" + type.name().toLowerCase());
        pixel = context.getPixel();

        x = speed > 0 ? Constants.WIDTH + 10 : -10;
        y = baseline;
    }

    @Override
    public void update(float dt) {
        time += dt;
        x -= speed * dt;

        y = baseline + Math.abs(5 * MathUtils.sin(time * 3 * speed / 10));

        if (x < -10 && speed > 0) remove = true;
        if (x > Constants.WIDTH + 10 && speed < 0) remove = true;
    }

    @Override
    public void render(SpriteBatch sb) {
        int x = snap(this.x);
        int y = snap(this.y);

        sb.setColor(1, 1, 1, 1);
        if (speed > 0) Utils.drawCentered(sb, image, x, y);
        else Utils.drawCenteredHFlip(sb, image, x, y);
    }

    public void renderShadow(SpriteBatch sb) {
        int diff = snap(MathUtils.clamp(y - baseline, 0, 2));
        sb.setColor(Constants.SHADOW);
        sb.draw(pixel, snap(x - (4 - diff)), snap(baseline - 3), 8 - diff * 2, 1);
    }
}
