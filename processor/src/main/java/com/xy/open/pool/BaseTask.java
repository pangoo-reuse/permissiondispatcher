package com.xy.open.pool;

import com.xy.open.bean.Property;

/**
 * Created by admin on 2017/4/18.
 */

public abstract class BaseTask extends Thread {

    private final Executor executor;

    public BaseTask() {
        executor = ExecutorManager.instants.getExecutor();
        executor.addTask(this);
    }

    private Property.Properties permission;
    private Object[] args;

    public void setData(Property.Properties permission, Object... args) {
        this.permission = permission;
        this.args = args;
    }

    public abstract void parse(Property.Properties permission, Object... args);

    @Override
    public void run() {
        parse(permission, args);
    }

    public boolean remove(Thread task) {
        return executor.remove(task);
    }
}
