package com.distraction.gloopgate.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;
import com.distraction.gloopgate.MoveTarget;
import com.distraction.gloopgate.entity.RepeatingBackground;

public class TitleScreen extends Screen {

    enum State {
        MAIN,
        DIFFICULTY
    }

    private State state = State.MAIN;

    private float time;

    private final RepeatingBackground slimeBg;

    private final TextureRegion title;
    private float titleOffset;

    private final MoveTarget titley;
    private final MoveTarget playHelpy;

    private final BitmapFont font;
    private int selection;

    public TitleScreen(Context context) {
        super(context);

        title = context.getImage("title");

        titley = new MoveTarget(1);
        titley.setTarget(Constants.HEIGHT, 17);

        playHelpy = new MoveTarget(1);
        playHelpy.setTarget(-10, 13);

        font = context.getFont();
        font.setColor(Constants.BLACK);

        slimeBg = new RepeatingBackground(context.getImage("slimebg"));
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

        if (state == State.MAIN && !titley.isActive() && !playHelpy.isActive()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) decrement();
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) increment();
        }
    }

    @Override
    public void update(float dt) {
        slimeBg.update(dt);

        titley.update(dt);
        playHelpy.update(dt);

        time += dt;
        titleOffset = MathUtils.sin(time * 4);
    }

    @Override
    public void render() {
        sb.begin();

        sb.setColor(Constants.TITLE_BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);

        sb.setColor(Constants.SLIME_BG);
        slimeBg.render(sb);

        sb.setProjectionMatrix(cam.combined);
        sb.setColor(1, 1, 1, 1);
        sb.draw(title, 6, snap(titley.value + titleOffset));

        font.draw(sb, "Play", 6, playHelpy.value);
        font.draw(sb, "Help", 36, playHelpy.value);
        sb.setColor(Constants.BLACK);
        if (!playHelpy.isActive() && !titley.isActive()) {
            sb.draw(pixel, selection == 0 ? 5 : 35, playHelpy.value - 10, 22, 1);
        }

        sb.end();
    }
}
