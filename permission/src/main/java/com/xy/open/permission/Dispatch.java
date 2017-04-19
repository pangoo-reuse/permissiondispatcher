package com.xy.open.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.xy.open.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.xy.open.permission.PermissionUtils.checkM;
import static com.xy.open.permission.PermissionUtils.requestPermissions;
/**
 * Created by admin on 2017/4/10.
 */

public class Dispatch {
    private Activity activity;
    private Map<Integer, String[]> permissions = new ConcurrentHashMap<Integer, String[]>();
    private Map<Integer, String> denyParams = new ConcurrentHashMap<Integer, String>();
    private Fragment fragment;
    private RequestListener checkedListener;

    public void register(@NonNull Activity activity) {
        if (activity != null) {
            this.activity = activity;
        }
    }

    @Deprecated
    public void register(Fragment fragment) {
        if (fragment != null) {
            this.fragment = fragment;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        String[] pms = this.permissions.get(requestCode);
        if (pms != null && pms.length != 0 && grantResults != null) {
            boolean verify = PermissionUtils.verifyPermissions(grantResults);
            if (verify) {
                // permission
                if (checkedListener != null) {
                    checkedListener.requestResult(requestCode, permissions, verify);
                }
            } else {
                requestPermission(requestCode, permissions);
            }
        }
    }

    public void requestPermission(int grantCode, String... permission) {
        if (checkM()) {
            String denyMessage = activity.getString(R.string.no_grant_str);
            if (denyParams.size() != 0) {
                denyMessage = denyParams.get(grantCode);
            }
            boolean request = checkNeedGrantedPermission(grantCode, denyMessage, permission);
            if (request) {
                String[] permissions = this.permissions.get(grantCode);
                if (checkShowDialog(permissions)) {
                    showGrant(grantCode);
                } else {
                    requestPermissions(getActivity(), grantCode, permissions);
                }
            } else {
                //
            }
            ;
        } else {
            //
        }
    }

    private void showGrant(final int grantCode) {
        AlertDialog dialog = new AlertDialog.Builder(activity).setIcon(R.drawable.safe)
                .setTitle(R.string.dialog_title)
                .setMessage(denyParams.size() == 0 ? activity.getString(R.string.no_grant_str) : denyParams.get(grantCode))
                .setPositiveButton(R.string.deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkedListener != null) {
                            checkedListener.requestResult(grantCode, permissions.get(grantCode), false);
                        }
                    }
                }).setNegativeButton(R.string.promise, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkM()) {
                            requestPermissions(getActivity(), grantCode, permissions.get(grantCode));
                        }
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

    }

    private boolean checkShowDialog(String... permission) {
        boolean promise = false;
        if (permission != null && permission.length != 0) {
            if (checkM()) {
                promise = PermissionUtils.shouldShowRequestPermissionRationale(activity, permission);
            }
        }
        return promise;
    }

    public void setCheckedListener(RequestListener checkedListener) {
        this.checkedListener = checkedListener;
    }

    public Activity getActivity() {
        return activity;
    }

    public boolean checkNeedGrantedPermission(int grantCode, String denyMessage, String... permissions) {
        List<String> noGrantPer = new ArrayList<String>();
        boolean needGrant = false;
        if (checkM()) {
            if (permissions != null && permissions.length != 0) {
                for (String permission : permissions) {
                    boolean hasSelfPermissions = PermissionUtils.hasSelfPermissions(activity, permission);
                    if (!hasSelfPermissions) {
                        noGrantPer.add(permission);
                        needGrant = true;
                    }
                }
                denyParams.put(grantCode, denyMessage);
                this.permissions.put(grantCode, noGrantPer.toArray(new String[noGrantPer.size()]));
            }
        }
        return needGrant;
    }
}
