package com.distraction.gloopgate.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;
import com.distraction.gloopgate.entity.RepeatingBackground;

public class TitleScreen extends Screen {

    private float time;

    private final RepeatingBackground bg;

    private final TextureRegion title;
    private float titley;

    private final BitmapFont font;
    private int selection;

    public TitleScreen(Context context) {
        super(context);

        title = context.getImage("title");

        font = context.getFont();
        font.setColor(Constants.BLACK);

        bg = new RepeatingBackground(context.getImage("slimebg"));
    }

    private void increment() {
        if (selection < 1) selection++;
    }

    private void decrement() {
        if (selection > 0) selection--;
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) decrement();
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) increment();
    }

    @Override
    public void update(float dt) {
        time += dt;
        titley = MathUtils.sin(time * 4);
        bg.update(dt);
    }

    @Override
    public void render() {
        sb.begin();

        sb.setColor(Constants.TITLE_BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);

        sb.setColor(Constants.SLIME_BG);
        bg.render(sb);

        sb.setProjectionMatrix(cam.combined);
        sb.setColor(1, 1, 1, 1);
        sb.draw(title, 6, snap(17 + titley));

        font.draw(sb, "Play", 6, 13);
        font.draw(sb, "Help", 36, 13);
        sb.setColor(Constants.BLACK);
        sb.draw(pixel, selection == 0 ? 5 : 35, 3, 22, 1);

        sb.end();
    }
}
