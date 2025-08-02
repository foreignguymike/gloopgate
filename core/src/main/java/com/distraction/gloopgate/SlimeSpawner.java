package com.distraction.gloopgate;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class SlimeSpawner {

    public interface SpawnListener {
        void onSpawn(int lane);
    }

    private float time;

    private final SpawnListener listener;
    private final int maxLanes;
    private final float interval;

    private final FixedSizeQueue<Integer> queue;

    public SlimeSpawner(SpawnListener listener, int maxLanes, float interval) {
        this.listener = listener;
        this.maxLanes = maxLanes;
        this.interval = interval;

        queue = new FixedSizeQueue<>(3);
    }

    public void update(float dt) {
        time -= dt;

        if (time < 0) {
            time += interval;
            spawn();
        }
    }

    private void spawn() {
        int nextLane = getNextLane();
        queue.offer(nextLane);
        listener.onSpawn(nextLane);
    }

    private int getNextLane() {
        List<Integer> validList = new ArrayList<>();
        for (int i = 0; i < maxLanes; i++) validList.add(i);
        Deque<Integer> deque = queue.getDeque();
        for (Integer i : deque) validList.remove(i);
        return validList.get(MathUtils.random(0, validList.size() - 1));
    }

}
