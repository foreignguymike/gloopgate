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

    public Context() {
        assets = new AssetManager();
        assets.load(ATLAS, TextureAtlas.class);
        assets.load(M5X716, BitmapFont.class);
        assets.finishLoading();

        sb = new SpriteBatch();

        audio = new AudioHandler();

        sm = new ScreenManager(new com.distraction.gloopgate.screens.TitleScreen(this));
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
        return getFont(M5X716);
    }

    public BitmapFont getFont(String name) {
        return getFont(name, 1f);
    }

    public BitmapFont getFont(String name, float scale) {
        BitmapFont originalFont = assets.get(name, BitmapFont.class);
        BitmapFont scaledFont = new BitmapFont(originalFont.getData().getFontFile(), originalFont.getRegion(), false);
        scaledFont.getData().setScale(scale);
        return scaledFont;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void resetScore() {
        score = 0;
    }

    public void dispose() {
        sb.dispose();
    }

}
