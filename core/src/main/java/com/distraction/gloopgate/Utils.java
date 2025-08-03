package com.distraction.gloopgate;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Utils {

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y) {
        sb.draw(
            image,
            MathUtils.round(x - image.getRegionWidth() / 2f) - 0.1f,
            MathUtils.round(y - image.getRegionHeight() / 2f) - 0.1f,
            image.getRegionWidth() + 0.2f,
            image.getRegionHeight() + 0.2f
        );
    }

}
