package com.distraction.gloopgate;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import java.util.List;
import java.util.NoSuchElementException;

public class Utils {

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y) {
        sb.draw(
            image,
            MathUtils.round(x - image.getRegionWidth() / 2f),
            MathUtils.round(y - image.getRegionHeight() / 2f),
            image.getRegionWidth(),
            image.getRegionHeight()
        );
    }

    public static void drawCenteredHFlip(SpriteBatch sb, TextureRegion image, float x, float y) {
        sb.draw(
            image,
            MathUtils.round(x + image.getRegionWidth() / 2f),
            MathUtils.round(y - image.getRegionHeight() / 2f),
            -image.getRegionWidth(),
            image.getRegionHeight()
        );
    }

    public static <T> T getLast(List<T> list) {
        if (list == null || list.isEmpty()) throw new NoSuchElementException("List is empty");
        return list.get(list.size() - 1);
    }

}
