package com.distraction.gloopgate.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.gloopgate.Context;

import java.util.List;

public class Valid extends Entity {

    public enum Type {
        VALID,
        INVALID
    }

    private final TextureRegion valid;
    private final TextureRegion invalid;
    private final TextureRegion colorOutline;
    private final TextureRegion color;

    private final Type type;
    private final List<Slime.Type> slimeTypes;

    public Valid(Context context, Type type, List<Slime.Type> slimeTypes) {
        this.type = type;
        this.slimeTypes = slimeTypes;

        valid = context.getImage("o");
        invalid = context.getImage("x");
        colorOutline = context.getImage("coloroutline");
        color = context.getImage("color");

        x = 0;
        y = 51;
    }

    public boolean isValid(Slime.Type type) {
        boolean inList = slimeTypes.contains(type);
        return (this.type == Type.VALID) == inList;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        sb.draw(type == Type.VALID ? valid : invalid, x, y);

        for (int i = 0; i < slimeTypes.size(); i++) {
            Slime.Type slimeType = slimeTypes.get(i);
            sb.draw(colorOutline, x + 19 + i * 11, y + 4);
            sb.setColor(slimeType.color);
            sb.draw(color, x + 20 + i * 11, y + 5);
        }
    }
}
