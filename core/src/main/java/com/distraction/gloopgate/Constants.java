package com.distraction.gloopgate;

import com.badlogic.gdx.graphics.Color;

@SuppressWarnings("all")
public class Constants {

    public static final String TITLE = "Slime Time Tally";
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    public static final int SCALE = 10;
    public static final int SWIDTH = WIDTH * SCALE;
    public static final int SHEIGHT = HEIGHT * SCALE;

    public static final boolean FULLSCREEN = false;

    public static final String VERSION = "v1.0.0";

    // Endesga 32 https://lospec.com/palette-list/endesga-32
    public static final Color[] COLORS = new Color[]{
        Color.valueOf("be4a2f"), // 0
        Color.valueOf("d77643"), // 1
        Color.valueOf("ead4aa"), // 2
        Color.valueOf("e4a672"), // 3
        Color.valueOf("b86f50"), // 4
        Color.valueOf("733e39"), // 5
        Color.valueOf("3e2731"), // 6
        Color.valueOf("a22633"), // 7
        Color.valueOf("e43b44"), // 8
        Color.valueOf("f77622"), // 9
        Color.valueOf("feae34"), // 10
        Color.valueOf("fee761"), // 11
        Color.valueOf("63c74d"), // 12
        Color.valueOf("3e8948"), // 13
        Color.valueOf("265c42"), // 14
        Color.valueOf("193c3e"), // 15
        Color.valueOf("124e89"), // 16
        Color.valueOf("0099db"), // 17
        Color.valueOf("2ce8f5"), // 18
        Color.valueOf("ffffff"), // 19
        Color.valueOf("c0cbdc"), // 20
        Color.valueOf("8b9bb4"), // 21
        Color.valueOf("5a6988"), // 22
        Color.valueOf("3a4466"), // 23
        Color.valueOf("262b44"), // 24
        Color.valueOf("181425"), // 25
        Color.valueOf("ff0044"), // 26
        Color.valueOf("68386c"), // 27
        Color.valueOf("b55088"), // 28
        Color.valueOf("f6757a"), // 29
        Color.valueOf("e8b796"), // 30
        Color.valueOf("c28569"), // 31
    };

    public static final Color LIGHT = COLORS[19];
    public static final Color DARK = COLORS[24];
    public static final Color RED = COLORS[26];
    public static final Color GREEN = COLORS[12];
    public static final Color BLUE = COLORS[17];
    public static final Color YELLOW = COLORS[11];
    public static final Color PURPLE = COLORS[27];

    public static final Color BLACK = COLORS[25];
    public static final Color GRASS = COLORS[12];
    public static final Color SKY = COLORS[18];
}
