package com.xy.open.pool;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2017/4/18.
 */

public class Executor {
    private final int corePoolSize = 1;
    private final int maximumPoolSize = 1;
    private final long keepAliveTime = 100;
    private final TimeUnit unit = TimeUnit.MILLISECONDS;
    private final ThreadPoolExecutor threadPoolExecutor;

    public Executor() {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1);
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize, keepAliveTime, unit, queue/*, new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                return null;
            }
        }*/);
    }
    public boolean remove(Runnable task){
        return threadPoolExecutor.remove(task);
    }
    int i = 0;
    public void addTask(Runnable task) {
        threadPoolExecutor.execute(task);
    }
}
