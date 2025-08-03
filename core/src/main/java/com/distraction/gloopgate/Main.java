package com.distraction.gloopgate;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private static final float TICK = 1f / 60f;
    private float accum;

    private Context context;

    @Override
    public void create() {
        context = new Context();

        Gdx.input.setCatchKey(Input.Keys.UP, true);
        Gdx.input.setCatchKey(Input.Keys.DOWN, true);
    }

    @Override
    public void resize(int width, int height) {
        context.sm.resize(width, height);
    }

    @Override
    public void render() {
        context.sm.input();
        accum += Gdx.graphics.getDeltaTime();
        while (accum > TICK) {
            accum -= TICK;
            context.sm.update(TICK);
        }
        context.sm.render();
    }

    @Override
    public void dispose() {
        context.dispose();
    }
}
