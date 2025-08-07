package com.distraction.gloopgate.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;

public class Message extends Entity {

    private static final float INTERVAL = 2;

    private final TextEntity text;
    private final String[] texts;
    private final Color bgColor;
    private final TextureRegion pixel;

    private float time;
    private int index;

    public Message(Context context, String[] texts) {
        this(context, texts, null);
    }

    public Message(Context context, String[] texts, Valid.Type type) {
        this.texts = texts;
        if (type == null) bgColor = Constants.MESSAGE_BLACK;
        else if (type == Valid.Type.VALID) bgColor = Constants.MESSAGE_VALID;
        else bgColor = Constants.MESSAGE_INVALID;

        pixel = context.getPixel();

        w = Constants.WIDTH;
        h = 14;

        x = 0;
        y = snap(Constants.HEIGHT / 2f - h / 2f + 1);

        BitmapFont font = context.getFont();
        text = new TextEntity(font, texts[0], Constants.WIDTH / 2f, y + 6, TextEntity.Alignment.CENTER);
    }

    @Override
    public void update(float dt) {
        time += dt;
        if (texts.length > 1) {
            if (time >= INTERVAL) {
                time -= INTERVAL;
                index++;
                if (index >= texts.length) index = 0;
                text.setText(texts[index]);
            }
        }
        text.y = y + 6;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(bgColor);
        sb.draw(pixel, x, y, w, h);
        sb.setColor(1, 1, 1, 1);
        text.render(sb);
    }
}
