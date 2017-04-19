package com.xy.open.permission;

/**
 * Created by admin on 2017/4/12.
 */

public interface RequestListener {
    void requestResult(int requestCode, String[] permissions , boolean fromUserAuthorize);

}
