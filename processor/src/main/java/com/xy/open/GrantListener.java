package com.xy.open;

/**
 * Created by admin on 2017/4/19.
 */

public interface GrantListener<T>  {
    void onPromise(boolean promise,T result);
}
