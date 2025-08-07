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

    public static final String VERSION = "v1.11";

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

    public static final Color TITLE_BG = COLORS[17];
    public static final Color SLIME_BG = COLORS[22];

    public static final Color RESULTS_BG = COLORS[30];
    public static final Color RESULTS_SLIME_BG = COLORS[31];

    public static final Color SUBMIT_BG = COLORS[12];
    public static final Color SUBMIT_SLIME_BG = COLORS[13];

    public static final Color LIGHT = COLORS[19];
    public static final Color DARK = COLORS[24];
    public static final Color RED = COLORS[26];
    public static final Color GREEN = COLORS[12];
    public static final Color BLUE = COLORS[17];
    public static final Color YELLOW = COLORS[11];
    public static final Color PURPLE = COLORS[27];

    public static final Color BLACK = COLORS[25];
    public static final Color WHITE = COLORS[19];
    public static final Color GRASS = COLORS[12];
    public static final Color SKY = COLORS[18];
    public static final Color SHADOW = COLORS[4];

    public static final Color MESSAGE_BLACK = COLORS[25];
    public static final Color MESSAGE_VALID = COLORS[13];
    public static final Color MESSAGE_INVALID = COLORS[7];

    public static String APP_ID = "";
    public static String API_KEY = "";
    public static int LEADERBOARD1 = 0;
    public static int LEADERBOARD2 = 0;
    public static int LEADERBOARD3 = 0;
    public static int LEADERBOARD4 = 0;
    public static int LEADERBOARD5 = 0;

    // not for you
    static {
//        APP_ID = ApiConstants.APP_ID;
//        API_KEY = ApiConstants.API_KEY;
//        LEADERBOARD1 = ApiConstants.LEADERBOARD1;
//        LEADERBOARD2 = ApiConstants.LEADERBOARD2;
//        LEADERBOARD3 = ApiConstants.LEADERBOARD3;
//        LEADERBOARD4 = ApiConstants.LEADERBOARD4;
//        LEADERBOARD5 = ApiConstants.LEADERBOARD5;
    }
}
