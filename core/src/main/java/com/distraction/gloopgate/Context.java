package com.distraction.gloopgate;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.gloopgate.screens.ScreenManager;

public class Context {

    private static final String ATLAS = "gloopgate.atlas";

    public AssetManager assets;

    public ScreenManager sm;
    public SpriteBatch sb;

    public Context() {
        assets = new AssetManager();
        assets.load(ATLAS, TextureAtlas.class);
        assets.finishLoading();

        sb = new SpriteBatch();

        sm = new ScreenManager(new com.distraction.gloopgate.screens.PlayScreen(this));
    }

    public TextureRegion getImage(String key) {
        TextureRegion region = assets.get(ATLAS, TextureAtlas.class).findRegion(key);
        if (region == null) throw new IllegalArgumentException("image " + key + " not found");
        return region;
    }

    public TextureRegion getPixel() {
        return getImage("pixel");
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

    public void dispose() {
        sb.dispose();
    }

}
