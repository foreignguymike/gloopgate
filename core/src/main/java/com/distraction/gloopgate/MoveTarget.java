package com.distraction.gloopgate;

import com.badlogic.gdx.math.Interpolation;

public class MoveTarget {

    public final float duration;

    public final Interpolation interpolation = Interpolation.swing;

    public float value;

    private float startValue;
    private float target;

    private float time;
    private boolean active;

    public MoveTarget(float duration) {
        this.duration = duration;
    }

    public void setTarget(float startValue, float target) {
        this.startValue = startValue;
        this.target = target;
        active = true;
    }

    public void update(float dt) {
        if (!active) return;

        time += dt;
        float d = time / duration;
        if (d >= 1f) {
            value = target;
            active = false;
        } else {
            float t = interpolation.apply(d);
            value = startValue + (target - startValue) * t;
        }
    }

    public boolean isActive() {
        return active;
    }
}
