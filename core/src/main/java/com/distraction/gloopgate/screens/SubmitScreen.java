package com.distraction.gloopgate.screens;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.B;
import static com.badlogic.gdx.Input.Keys.BACKSPACE;
import static com.badlogic.gdx.Input.Keys.C;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.E;
import static com.badlogic.gdx.Input.Keys.ENTER;
import static com.badlogic.gdx.Input.Keys.F;
import static com.badlogic.gdx.Input.Keys.G;
import static com.badlogic.gdx.Input.Keys.H;
import static com.badlogic.gdx.Input.Keys.I;
import static com.badlogic.gdx.Input.Keys.J;
import static com.badlogic.gdx.Input.Keys.K;
import static com.badlogic.gdx.Input.Keys.L;
import static com.badlogic.gdx.Input.Keys.M;
import static com.badlogic.gdx.Input.Keys.N;
import static com.badlogic.gdx.Input.Keys.NUM_0;
import static com.badlogic.gdx.Input.Keys.NUM_1;
import static com.badlogic.gdx.Input.Keys.NUM_2;
import static com.badlogic.gdx.Input.Keys.NUM_3;
import static com.badlogic.gdx.Input.Keys.NUM_4;
import static com.badlogic.gdx.Input.Keys.NUM_5;
import static com.badlogic.gdx.Input.Keys.NUM_6;
import static com.badlogic.gdx.Input.Keys.NUM_7;
import static com.badlogic.gdx.Input.Keys.NUM_8;
import static com.badlogic.gdx.Input.Keys.NUM_9;
import static com.badlogic.gdx.Input.Keys.O;
import static com.badlogic.gdx.Input.Keys.P;
import static com.badlogic.gdx.Input.Keys.Q;
import static com.badlogic.gdx.Input.Keys.R;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.SHIFT_LEFT;
import static com.badlogic.gdx.Input.Keys.SHIFT_RIGHT;
import static com.badlogic.gdx.Input.Keys.SPACE;
import static com.badlogic.gdx.Input.Keys.T;
import static com.badlogic.gdx.Input.Keys.U;
import static com.badlogic.gdx.Input.Keys.V;
import static com.badlogic.gdx.Input.Keys.W;
import static com.badlogic.gdx.Input.Keys.X;
import static com.badlogic.gdx.Input.Keys.Y;
import static com.badlogic.gdx.Input.Keys.Z;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;
import com.distraction.gloopgate.LevelData;
import com.distraction.gloopgate.MoveTarget;
import com.distraction.gloopgate.entity.Message;
import com.distraction.gloopgate.entity.RepeatingBackground;
import com.distraction.gloopgate.entity.TextEntity;

import java.util.HashMap;
import java.util.Map;

public class SubmitScreen extends Screen {

    private static final int MAX_CHARS = 3;
    private static final Map<Integer, String> INPUT_MAP = new HashMap<Integer, String>() {{
        put(A, "a");
        put(B, "b");
        put(C, "c");
        put(D, "d");
        put(E, "e");
        put(F, "f");
        put(G, "g");
        put(H, "h");
        put(I, "i");
        put(J, "j");
        put(K, "k");
        put(L, "l");
        put(M, "m");
        put(N, "n");
        put(O, "o");
        put(P, "p");
        put(Q, "q");
        put(R, "r");
        put(S, "s");
        put(T, "t");
        put(U, "u");
        put(V, "v");
        put(W, "w");
        put(X, "x");
        put(Y, "y");
        put(Z, "z");
        put(SPACE, " ");
    }};

    private final LevelData.Difficulty difficulty;
    private final int score;

    private final RepeatingBackground slimeBg;

    private final Message title;

    private final MoveTarget titley;
    private final MoveTarget datay;

    private final TextEntity enterNameText;
    private final TextEntity nameText;
    private float caretTime;
    private boolean loading;

    private float time;

    public SubmitScreen(Context context, LevelData.Difficulty difficulty, int score) {
        super(context);
        this.difficulty = difficulty;
        this.score = score;

        ignoreInput = true;
        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        in.start();
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f, () -> context.sm.replace(new TitleScreen(context)));

        slimeBg = new RepeatingBackground(context.getImage("slimebg"));

        title = new Message(context, new String[]{"New Score!"});

        titley = new MoveTarget(Constants.HEIGHT + 10);
        titley.interpolation = Interpolation.smooth;
        titley.setTarget(Constants.HEIGHT - 8, 0.3f);
        datay = new MoveTarget(-Constants.HEIGHT);
        datay.interpolation = Interpolation.smooth;
        datay.setTarget(0, 0.3f);

        BitmapFont font = context.getSmallFont();
        enterNameText = new TextEntity(font, "Enter name", Constants.WIDTH / 2f, 50, TextEntity.Alignment.CENTER);
        enterNameText.setColor(Constants.BLACK);
        nameText = new TextEntity(font, context.name, Constants.WIDTH / 2f, 30, TextEntity.Alignment.CENTER);
        nameText.setColor(Constants.BLACK);

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keycode) {
                if (ignoreInput) return true;
                String letter = INPUT_MAP.get(keycode);
                if (letter != null) {
                    if (context.name.length() < MAX_CHARS) {
                        context.name += letter.toUpperCase();
                        caretTime = 0;
                    }
                }
                if (keycode == BACKSPACE) {
                    if (!context.name.isEmpty()) {
                        context.name = context.name.substring(0, context.name.length() - 1);
                        caretTime = 0;
                    }
                }
                if (keycode == ENTER) {
                    submitName();
                }
                if (context.name != null) {
                    nameText.setText(context.name);
                }
                return true;
            }
        });
    }

    private void submitName() {
        if (context.name.isEmpty() || !context.leaderboardsInitialized) return;
        if (loading) return;
        loading = true;
        context.audio.playSound("click");
        context.submitScore(context.name, difficulty, context.score, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String res = httpResponse.getResultAsString();
                // throwing an exception with SubmitScoreResponse here for some reason
                // just doing a sus true check instead
                if (res.contains("true")) {
                    context.fetchLeaderboard(success -> {
                        ignoreInput = true;
                        out.start();
                        Gdx.input.setInputProcessor(null);
                        context.audio.playSound("submit");
                    });
                } else {
                    failed(null);
                }
            }

            @Override
            public void failed(Throwable t) {
                ignoreInput = false;
                loading = false;
            }

            @Override
            public void cancelled() {
                failed(null);
            }
        });
    }

    @Override
    public void input() {
        // noop custom input processor
    }

    @Override
    public void update(float dt) {
        time += dt;

        in.update(dt);
        out.update(dt);

        slimeBg.update(dt);

        titley.update(dt);
        datay.update(dt);

        title.y = titley.value - 5;
        title.update(dt);

        enterNameText.y = datay.value + 40;
        nameText.y = datay.value + 25;

        caretTime += dt;
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);

        sb.setColor(Constants.SUBMIT_BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        sb.setColor(Constants.SUBMIT_SLIME_BG);
        slimeBg.render(sb);

        title.render(sb);
        enterNameText.render(sb);
        nameText.render(sb);

        sb.setColor(Constants.BLACK);
        if (caretTime % 1 < 0.5f) {
            sb.draw(pixel, context.name.isEmpty() ? Constants.WIDTH / 2f - 3 : nameText.x + nameText.w / 2f, nameText.y - 7, 5, 1);
        }

        sb.setColor(Constants.WHITE);
        if (loading) {
            for (int i = 0; i < 5; i++) {
                float x = Constants.WIDTH / 2f + 10 * MathUtils.cos(-6f * time + i * 0.1f);
                float y = Constants.WIDTH / 2f + 10 * MathUtils.sin(-6f * time + i * 0.1f) - 5;
                sb.draw(pixel, x, y, 2, 2);
            }
        }

        in.render(sb);
        out.render(sb);

        sb.end();
    }
}
