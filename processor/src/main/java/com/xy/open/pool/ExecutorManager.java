package com.xy.open.pool;

/**
 * Created by admin on 2017/4/18.
 */

public enum ExecutorManager {
    instants;
    private final Executor executor;

    ExecutorManager() {
        executor = new Executor();
    }

    public Executor getExecutor() {
        return executor;
    }
}
