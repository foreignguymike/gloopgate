package com.distraction.gloopgate;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.gloopgate.audio.AudioHandler;
import com.distraction.gloopgate.screens.ScreenManager;

public class Context {

    private static final String ATLAS = "gloopgate.atlas";
    public static final String M5X716 = "fonts/m5x716.fnt";

    public AssetManager assets;
    public AudioHandler audio;

    public ScreenManager sm;
    public SpriteBatch sb;

    public int score = 100;

    private final BitmapFont font;

    public boolean loaded;

    public Context() {
        assets = new AssetManager();
        assets.load(ATLAS, TextureAtlas.class);
        assets.load(M5X716, BitmapFont.class);
        assets.finishLoading();

        font = assets.get(M5X716, BitmapFont.class);

        sb = new SpriteBatch();

        audio = new AudioHandler();

        sm = new ScreenManager(new com.distraction.gloopgate.screens.TitleScreen(this));
        loaded = true;
    }

    public TextureRegion getImage(String key) {
        TextureRegion region = assets.get(ATLAS, TextureAtlas.class).findRegion(key);
        if (region == null) throw new IllegalArgumentException("image " + key + " not found");
        return region;
    }

    public TextureRegion getPixel() {
        return getImage("pixel");
    }

    public BitmapFont getFont() {
        return font;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void resetScore() {
        score = 0;
    }

    public void dispose() {
        sb.dispose();
        font.dispose();
    }

}
