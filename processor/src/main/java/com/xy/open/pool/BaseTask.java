package com.xy.open.pool;

/**
 * Created by admin on 2017/4/18.
 */

public abstract class BaseTask extends Thread {

    private final Executor executor;
    public BaseTask() {
        executor = ExecutorManager.instants.getExecutor();
    }
    public boolean remove(Thread task) {
        return executor.remove(task);
    }
    public boolean runTask(){
        executor.addTask(this);
        return executor.running(this);
    }
}
