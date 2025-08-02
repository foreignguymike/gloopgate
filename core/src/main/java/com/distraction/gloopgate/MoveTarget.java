package com.distraction.gloopgate;

import com.badlogic.gdx.math.Interpolation;

public class MoveTarget {

    public final Interpolation interpolation = Interpolation.swing;

    public float value;

    private float startValue;
    private float target;

    private float time;
    private float duration;
    private boolean active;

    public MoveTarget(float startValue) {
        this.startValue = this.value = startValue;
    }

    public void setTarget(float target) {
        setTarget(target, 1f);
    }

    public void setTarget(float target, float duration) {
        this.target = target;
        this.duration = duration;
        this.startValue = value;
        time = 0;
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
