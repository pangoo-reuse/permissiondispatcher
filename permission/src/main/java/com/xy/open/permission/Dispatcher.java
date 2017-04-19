package com.xy.open.permission;


/**
 * Created by admin on 2017/4/10.
 */

public enum Dispatcher {
    instants;
    private Dispatch dispatch;
    Dispatcher() {
        dispatch = new Dispatch();
    }
    public Dispatch getDispatcher() {
        return dispatch;
    }
}
