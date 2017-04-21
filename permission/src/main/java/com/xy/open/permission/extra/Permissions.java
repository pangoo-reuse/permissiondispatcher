package com.xy.open.permission.extra;

import android.Manifest;

import com.xy.open.RequestPermissions;

/**
 * Created by yonehsiung@gmail.com on 2017/4/21.
 */

public class Permissions {
    @RequestPermissions(permissions = {Manifest.permission.WRITE_CONTACTS},
            grantCode = 20000, denyMessage = "我们需要写入联系人", invoke = false)
    public void writeContacts() {
    }

    @RequestPermissions(permissions = {Manifest.permission.GET_ACCOUNTS},
            grantCode = 20001, denyMessage = "我们需要查找设备上的帐户", invoke = false)
    public void getAccounts() {
    }

    @RequestPermissions(permissions = {Manifest.permission.READ_CONTACTS},
            grantCode = 20002, denyMessage = "我们需要读取联系人", invoke = false)
    public void readContacts() {
    }

    @RequestPermissions(permissions = {Manifest.permission.READ_CALL_LOG},
            grantCode = 20003, denyMessage = "我们需要读取通话记录", invoke = false)
    public void readCallLog() {
    }

    @RequestPermissions(permissions = {Manifest.permission.READ_PHONE_STATE},
            grantCode = 20004, denyMessage = "我们需要读取电话状态", invoke = false)
    public void readPhoneState() {
    }

    @RequestPermissions(permissions = {Manifest.permission.CALL_PHONE},
            grantCode = 20005, denyMessage = "我们需要拨打电话", invoke = false)
    public void callPhone() {
    }

    @RequestPermissions(permissions = {Manifest.permission.WRITE_CALL_LOG},
            grantCode = 20006, denyMessage = "我们需要修改通话记录", invoke = false)
    public void writeCallLog() {
    }

    @RequestPermissions(permissions = {Manifest.permission.USE_SIP},
            grantCode = 20007, denyMessage = "我们需要SIP视频服务", invoke = false)
    public void useSip() {
    }

    @RequestPermissions(permissions = {Manifest.permission.PROCESS_OUTGOING_CALLS},
            grantCode = 20008, denyMessage = "我们需要拨出电话", invoke = false)
    public void processOutgoingCalls() {
    }

    @RequestPermissions(permissions = {Manifest.permission.ADD_VOICEMAIL},
            grantCode = 20009, denyMessage = "我们需要添加语音邮件", invoke = false)
    public void addVoicemail() {
    }

    @RequestPermissions(permissions = {Manifest.permission.WRITE_CALENDAR},
            grantCode = 200010, denyMessage = "我们需要修改日历", invoke = false)
    public void writeCalendar() {
    }

    @RequestPermissions(permissions = {Manifest.permission.CAMERA},
            grantCode = 200011, denyMessage = "我们需要拍照", invoke = false)
    public void camera() {
    }

    @RequestPermissions(permissions = {Manifest.permission.BODY_SENSORS},
            grantCode = 200012, denyMessage = "我们需要获取传感器", invoke = false)
    public void bodySensors() {
    }

    @RequestPermissions(permissions = {Manifest.permission.ACCESS_FINE_LOCATION},
            grantCode = 200013, denyMessage = "我们需要GPS获取定位", invoke = false)
    public void gpsLocation() {
    }

    @RequestPermissions(permissions = {Manifest.permission.ACCESS_COARSE_LOCATION},
            grantCode = 200014, denyMessage = "我们需要定位", invoke = false)
    public void accessCoarseLocation() {
    }

    @RequestPermissions(permissions = {Manifest.permission.READ_EXTERNAL_STORAGE},
            grantCode = 200015, denyMessage = "我们需要读取内存卡", invoke = false)
    public void readExternalStorage() {
    }

    @RequestPermissions(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE},
            grantCode = 200016, denyMessage = "我们需要写内存卡", invoke = false)
    public void writeExternalStorage() {
    }

    @RequestPermissions(permissions = {Manifest.permission.RECORD_AUDIO},
            grantCode = 200017, denyMessage = "我们需要录音", invoke = false)
    public void recordAudio() {
    }

    @RequestPermissions(permissions = {Manifest.permission.READ_SMS},
            grantCode = 200018, denyMessage = "我们需要读取短信", invoke = false)
    public void readSms() {
    }

    @RequestPermissions(permissions = {Manifest.permission.RECEIVE_WAP_PUSH},
            grantCode = 200019, denyMessage = "我们需要接收WAP PUSH信息", invoke = false)
    public void receiveWapPush() {
    }

    @RequestPermissions(permissions = {Manifest.permission.RECEIVE_MMS},
            grantCode = 200020, denyMessage = "我们需要接收彩信", invoke = false)
    public void receiveMms() {
    }

    @RequestPermissions(permissions = {Manifest.permission.RECEIVE_SMS},
            grantCode = 200020, denyMessage = "我们需要接收短信", invoke = false)
    public void receiveSms() {
    }

    @RequestPermissions(permissions = {Manifest.permission.SEND_SMS},
            grantCode = 200021, denyMessage = "我们需要发送短信", invoke = false)
    public void sendSms() {
    }
    @RequestPermissions(permissions = {Manifest.permission.BROADCAST_WAP_PUSH},
            grantCode = 200022, denyMessage = "我们需要获取小区广播", invoke = false)
    public void broadcastWapPush() {
    }
    /**




     * Created by admin on 2017/4/19.
     * group:android.permission-group.CONTACTS
     permission:android.permission.WRITE_CONTACTS
     permission:android.permission.GET_ACCOUNTS
     permission:android.permission.READ_CONTACTS

     group:android.permission-group.PHONE
     permission:android.permission.READ_CALL_LOG
     permission:android.permission.READ_PHONE_STATE
     permission:android.permission.CALL_PHONE
     permission:android.permission.WRITE_CALL_LOG
     permission:android.permission.USE_SIP
     permission:android.permission.PROCESS_OUTGOING_CALLS
     permission:com.android.voicemail.permission.ADD_VOICEMAIL

     group:android.permission-group.CALENDAR
     permission:android.permission.READ_CALENDAR
     permission:android.permission.WRITE_CALENDAR

     group:android.permission-group.CAMERA
     permission:android.permission.CAMERA

     group:android.permission-group.SENSORS
     permission:android.permission.BODY_SENSORS

     group:android.permission-group.LOCATION
     permission:android.permission.ACCESS_FINE_LOCATION
     permission:android.permission.ACCESS_COARSE_LOCATION

     group:android.permission-group.STORAGE
     permission:android.permission.READ_EXTERNAL_STORAGE
     permission:android.permission.WRITE_EXTERNAL_STORAGE

     group:android.permission-group.MICROPHONE
     permission:android.permission.RECORD_AUDIO

     group:android.permission-group.SMS
     permission:android.permission.READ_SMS
     permission:android.permission.RECEIVE_WAP_PUSH
     permission:android.permission.RECEIVE_MMS
     permission:android.permission.RECEIVE_SMS
     permission:android.permission.SEND_SMS
     permission:android.permission.READ_CELL_BROADCASTS
     */
}
