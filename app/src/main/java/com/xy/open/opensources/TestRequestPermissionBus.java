package com.xy.open.opensources;

import android.Manifest;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import com.xy.open.GrantListener;
import com.xy.open.RequestPermissions;

/**
 * Created by admin on 2017/4/11.
 */
public class TestRequestPermissionBus {

    @RequestPermissions(permissions = {Manifest.permission.WAKE_LOCK, Manifest.permission.ACCESS_FINE_LOCATION},
            grantCode = 20000,denyMessage = "拒绝Test的权限应用将无法运行，很有可能死机哦")
    public final int test(int i, String m, Activity activity, GrantListener listener) throws Exception {
        Log.d("TAG_TEST", "test M = " + m + " i = " + i + "object = " + activity);
        return i;
    }

    @RequestPermissions(permissions = {Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, grantCode = 20001,
            denyMessage = "拒绝Test1的CALL_PHONE权限应用将无法运行，很有可能爆炸哦"
    )
    public void test1(int i1, String m1, Object object1) throws RuntimeException, Exception {
        Log.d("TAG_TEST", "test1 M = " + m1 + " i = " + i1 + "object = " + object1);
    }

    @RequestPermissions(permissions = {Manifest.permission.BLUETOOTH_ADMIN}, grantCode = 20005,
            denyMessage = "拒绝Test1的CALL_PHONE权限应用将无法运行，很有可能毁灭地球哦"
    )
    public final void test3(Object i3, String m3, int activity3) throws Exception {
        Log.d("TAG_TEST", "test3 M = " + m3 + " i3 = " + i3 + "object3 = " + activity3);
    }

    private void  setText(final String txt){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
            }
        });
    }
}
