package com.distraction.gloopgate.entity;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;

public class Counter extends Entity {

    private static final float BUTTON_INTERVAL = 0.05f;

    private final TextureRegion counter;
    private final TextureRegion button;

    private final BitmapFont font;

    public int count;
    private final String[] c;

    private float buttonTimer;
    private int buttonOffset;

    public Counter(Context context) {
        counter = context.getImage("counter");
        button = context.getImage("counterbutton");

        font = context.getFont();

        c = new String[] { "0", "0", "0" };

        x = 21;
        y = 0;
    }

    public void count() {
        count++;
        if (count > 999) {
            count = 999;
        }
        c[2] = (count % 10) + "";
        c[1] = ((count / 10) % 10) + "";
        c[0] = (count / 100) + "";

        buttonTimer = BUTTON_INTERVAL;
    }

    @Override
    public void update(float dt) {
        buttonTimer -= dt;
        y = buttonTimer < 0 ? 0 : -1;
        buttonOffset = buttonTimer < 0 ? 0 : 2;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        sb.draw(button, x + 23 - buttonOffset, y + 3);
        sb.draw(counter, x, y);

        font.setColor(Constants.BLACK);
        font.draw(sb, c[0], x + 3, y + 10);
        font.draw(sb, c[1], x + 9, y + 10);
        font.draw(sb, c[2], x + 15, y + 10);
    }
}
