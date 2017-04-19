package com.xy.open.opensources;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xy.open.GrantListener;
import com.xy.open.processor.generated.PermissionCheck;


public class MainActivity extends BaseActivity {

    private TextView tv;
    private TextView des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.tv);
        des = (TextView)findViewById(R.id.des);

        des.setText("request-permission \ncall method = <test>, origin int param <int = 45>\n\n");


    }

    public void request(View view) throws Exception {
        PermissionCheck.getInstants().checkTestWithPermissions(45, "test", this, new GrantListener<Integer>() {
            @Override
            public void onPromise(boolean promise, Integer result) {
                System.out.print(promise + " :::::  "+result);
                tv.setText("promise = " + promise + " , result =  "+result);
            }
        });
    }

    public void requests(View view) throws Exception {
        PermissionCheck.getInstants().checkTest1WithPermissions(2, "test1", new Object());

        PermissionCheck.getInstants().checkTest3WithPermissions(22, "test3", 111);
    }
}
