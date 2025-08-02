package com.distraction.gloopgate;

import com.badlogic.gdx.math.MathUtils;
import com.distraction.gloopgate.entity.Slime;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class SlimeSpawner {

    public interface SpawnListener {
        void onSpawn(Slime.Type type, int lane);
    }

    private float time;

    private final SpawnListener listener;

    private final int maxLanes;
    private final float interval;
    private final FixedSizeQueue<Integer> queue;

    private final Slime.Type[] types;
    private final int maxTypes;
    private final int biasAmount;
    private final int[] weights;
    private final int totalWeight;

    public SlimeSpawner(SpawnListener listener, int maxLanes, float interval, List<Slime.Type> bias, int biasAmount) {
        this.listener = listener;
        this.maxLanes = maxLanes;
        this.interval = interval;
        queue = new FixedSizeQueue<>(2);

        types = Slime.Type.values();
        maxTypes = types.length;
        this.biasAmount = biasAmount;
        weights = convertToWeights(bias);
        totalWeight = maxTypes - bias.size() + bias.size() * biasAmount;
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
        Slime.Type type = getNextType();
        listener.onSpawn(type, nextLane);
    }

    private int getNextLane() {
        List<Integer> validList = new ArrayList<>();
        for (int i = 0; i < maxLanes; i++) validList.add(i);
        Deque<Integer> deque = queue.getDeque();
        for (Integer i : deque) validList.remove(i);
        return validList.get(MathUtils.random(0, validList.size() - 1));
    }

    private Slime.Type getNextType() {
        int r = MathUtils.random(0, totalWeight);
        for (int i = 0; i < weights.length; i++) {
            int weight = weights[i];
            if (r < weight) return types[i];
            r -= weight;
        }
        return Slime.Type.random();
    }

    private int[] convertToWeights(List<Slime.Type> bias) {
        int[] intArray = new int[bias.size()];
        for (int i = 0; i < bias.size(); i++) {
            intArray[i] = bias.get(i).ordinal();
        }

        int[] ret = new int[maxTypes];
        for (int i = 0; i < maxTypes; i++) {
            ret[i] = contains(intArray, i) ? biasAmount : 1;
        }
        return ret;
    }

    private boolean contains(int[] arr, int target) {
        for (int i : arr) if (i == target) return true;
        return false;
    }

}
