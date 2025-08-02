package com.distraction.gloopgate.entity;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;

public class Message extends Entity {

    private final TextEntity[] texts;
    private final TextureRegion pixel;

    public Message(Context context, String[] texts) {
        this.texts = new TextEntity[texts.length];

        pixel = context.getPixel();

        w = Constants.WIDTH;
        h = texts.length * 10 + 4;

        x = 0;
        y = snap(Constants.HEIGHT / 2f - h / 2f);

        BitmapFont font = context.getFont();
        font.setColor(Constants.WHITE);
        for (int i = 0; i < texts.length; i++) {
            this.texts[i] = new TextEntity(font, texts[i], Constants.WIDTH / 2f, y + h - 8 - i * 10, TextEntity.Alignment.CENTER);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Constants.BLACK);
        sb.draw(pixel, x, y, w, h);
        sb.setColor(1, 1, 1, 1);
        for (TextEntity te : texts) te.render(sb);

    }
}
