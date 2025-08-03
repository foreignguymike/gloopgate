package com.distraction.gloopgate.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.gloopgate.Constants;
import com.distraction.gloopgate.Context;
import com.distraction.gloopgate.LevelData;
import com.distraction.gloopgate.MoveTarget;
import com.distraction.gloopgate.entity.RepeatingBackground;
import com.distraction.gloopgate.entity.TextEntity;

public class ResultScreen extends Screen {

    private final RepeatingBackground slimeBg;

    private final MoveTarget titley;
    private final MoveTarget datay;

    private final TextEntity titleText;
    private final TextEntity scoreText;
    private final TextEntity scoreTypeText;

    private final float barLength = 40;
    private final float barPercentMax;
    private float barPercent;

    public ResultScreen(Context context, LevelData.Difficulty difficulty, int score) {
        super(context);

        ignoreInput = true;
        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        in.start();
        out = new Transition(context, Transition.Type.FLASH_OUT, 0.5f, () -> context.sm.replace(new TitleScreen(context)));

        slimeBg = new RepeatingBackground(context.getImage("slimebg"));

        BitmapFont font = context.getFont();
        font.setColor(Constants.BLACK);

        titley = new MoveTarget(Constants.HEIGHT + 10);
        titley.setTarget(Constants.HEIGHT - 12);

        datay = new MoveTarget(-Constants.HEIGHT / 2f);
        datay.setTarget(8);

        titleText = new TextEntity(font, difficulty.text, Constants.WIDTH / 2f, titley.value, TextEntity.Alignment.CENTER);
        titleText.setColor(Constants.BLACK);

        scoreText = new TextEntity(font, score + "%", Constants.WIDTH / 2f, datay.value + 20, TextEntity.Alignment.CENTER);
        scoreText.setColor(Constants.BLACK);

        String text;
        if (score == 100) text = "PERFECT!";
        else if (score >= 95) text = "Amazing!";
        else if (score >= 90) text = "Great job";
        else if (score >= 80) text = "Okay.";
        else text = "You good...?";
        scoreTypeText = new TextEntity(font, text, Constants.WIDTH / 2f, datay.value + 8, TextEntity.Alignment.CENTER);
        scoreTypeText.setColor(Constants.BLACK);

        barPercentMax = score / 100f;

        context.audio.stopMusic();
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            ignoreInput = true;
            out.start();
        }
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);

        slimeBg.update(dt);

        titley.update(dt);
        datay.update(dt);

        titleText.y = titley.value;
        scoreText.y = datay.value + 24;
        scoreTypeText.y = datay.value + 2;

        barPercent += 0.5f * dt;
        if (barPercent > barPercentMax) barPercent = barPercentMax;
    }

    @Override
    public void render() {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);

        sb.setColor(Constants.RESULTS_BG);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        sb.setColor(Constants.RESULTS_SLIME_BG);
        slimeBg.render(sb);

        titleText.render(sb);
        scoreText.render(sb);
        scoreTypeText.render(sb);

        sb.setColor(Constants.BLACK);
        sb.draw(pixel, 12, datay.value + 16, barLength, 1);
        sb.draw(pixel, 11, datay.value + 14, barLength + 2, 2);
        sb.draw(pixel, 12, datay.value + 13, barLength, 1);
        sb.setColor(Constants.GREEN);
        sb.draw(pixel, 12, datay.value + 14, MathUtils.floor(barPercent * barLength), 2);

        in.render(sb);
        out.render(sb);

        sb.end();
    }
}
