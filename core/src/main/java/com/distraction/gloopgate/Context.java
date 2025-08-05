package com.distraction.gloopgate;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.gloopgate.audio.AudioHandler;
import com.distraction.gloopgate.gj.GameJoltClient;
import com.distraction.gloopgate.screens.ScreenManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import de.golfgl.gdxgamesvcs.leaderboard.ILeaderBoardEntry;

public class Context {

    private static final String ATLAS = "gloopgate.atlas";
    public static final String M5X716 = "fonts/m5x716.fnt";
    public static final String M3X616 = "fonts/m3x616.fnt";

    public static final int MAX_SCORES = 3;

    public AssetManager assets;
    public AudioHandler audio;

    public ScreenManager sm;
    public SpriteBatch sb;

    public int score = 100;

    private final BitmapFont font;
    private final BitmapFont font2;

    public boolean loaded;

    public GameJoltClient client;
    public boolean leaderboardsRequesting;
    public boolean leaderboardsInitialized;
    private int requestCount;
    private int successCount;
    public final List<List<ILeaderBoardEntry>> entries;

    public String name = "";

    public Context() {
        assets = new AssetManager();
        assets.load(ATLAS, TextureAtlas.class);
        assets.load(M3X616, BitmapFont.class);
        assets.load(M5X716, BitmapFont.class);
        assets.finishLoading();

        font = assets.get(M5X716, BitmapFont.class);
        font2 = assets.get(M3X616, BitmapFont.class);

        entries = new ArrayList<>();
        for (int i = 0; i < LevelData.Difficulty.values().length; i++) entries.add(new ArrayList<>());

        sb = new SpriteBatch();

        audio = new AudioHandler();

        client = new GameJoltClient();
        client.setGjScoreTableMapper(id -> {
            if (Objects.equals(id, "0")) return Constants.LEADERBOARD1;
            else if (Objects.equals(id, "1")) return Constants.LEADERBOARD2;
            else if (Objects.equals(id, "2")) return Constants.LEADERBOARD3;
            else return Constants.LEADERBOARD4;
        });
        client.initialize(Constants.APP_ID, Constants.API_KEY);

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

    public BitmapFont getSmallFont() {
        return font2;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void resetScore() {
        score = 0;
    }

    public void fetchLeaderboard(SuccessCallback callback) {
        if (Constants.LEADERBOARD1 == 0) {
            callback.callback(false);
            return;
        }
        if (leaderboardsRequesting) return;
        leaderboardsRequesting = true;
        List<LevelData.Difficulty> list = getDifficultiesToRequest();
        for (int i = 0; i < entries.size(); i++) {
            if (list.contains(LevelData.Difficulty.values()[i])) entries.get(i).clear();
        }
        requestCount = list.size();
        if (requestCount == 0) {
            callback.callback(true);
            leaderboardsInitialized = true;
        } else {
            successCount = 0;
            for (LevelData.Difficulty d : list) request(d, callback);
        }
    }

    private List<LevelData.Difficulty> getDifficultiesToRequest() {
        List<LevelData.Difficulty> ret = new ArrayList<>();
        for (LevelData.Difficulty d : LevelData.Difficulty.values()) {
            int total = 0;
            for (ILeaderBoardEntry entry : entries.get(d.ordinal())) {
                total += Integer.parseInt(entry.getFormattedValue());
            }
            if (total < 100 * MAX_SCORES) {
                ret.add(d);
            }
        }
        return ret;
    }

    private void request(LevelData.Difficulty difficulty, SuccessCallback callback) {
        System.out.println("fetching " + difficulty);
        client.fetchLeaderboardEntries(difficulty.ordinal() + "", MAX_SCORES, false, leaderBoard -> {
            requestCount--;
            if (requestCount == 0) {
                leaderboardsRequesting = false;
                leaderboardsInitialized = true;
            }
            if (leaderBoard != null) {
                successCount++;
                entries.get(difficulty.ordinal()).clear();
                for (int i = 0; i < leaderBoard.size; i++) {
                    entries.get(difficulty.ordinal()).add(leaderBoard.get(i));
                }
            }
            if (requestCount == 0) {
                callback.callback(successCount == LevelData.Difficulty.values().length);
            }
        });
    }

    public void submitScore(String name, LevelData.Difficulty difficulty, int score, Net.HttpResponseListener listener) {
        client.setGuestName(name);
        System.out.println("submitting " + difficulty + " " + score);
        client.submitToLeaderboard(difficulty.ordinal() + "", score, null, 10000, listener);
    }

    public boolean isHighscore(LevelData.Difficulty difficulty, int score) {
        if (!leaderboardsInitialized) return false;
        List<ILeaderBoardEntry> list = entries.get(difficulty.ordinal());
        return list.size() < MAX_SCORES || score > Integer.parseInt(Utils.getLast(list).getFormattedValue());
    }

    public void dispose() {
        sb.dispose();
        font.dispose();
    }

}
