package com.xy.open.opensources;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xy.open.permission.Dispatch;
import com.xy.open.permission.Dispatcher;

//import com.xy.open.permission.Dispatch;
//import com.xy.open.permission.Dispatcher;


/**
 * Created by admin on 2017/4/18.
 */

public abstract  class BaseActivity extends Activity {
    private Dispatch dispatcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dispatcher = Dispatcher.instants.getDispatcher();
        dispatcher.register(this);
        new TestRequestPermissionBus();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        dispatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
