package com.distraction.gloopgate;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class FixedSizeQueue<E> {

    private final int maxSize;
    private final Deque<E> deque;

    public FixedSizeQueue(int maxSize) {
        this.maxSize = maxSize;
        deque = new ArrayDeque<>(maxSize);
    }

    public void offer(E e) {
        if (deque.size() == maxSize) deque.poll();
        deque.offer(e);
    }

    public Deque<E> getDeque() {
        return deque;
    }

    public boolean contains(E e) {
        return deque.contains(e);
    }

}
