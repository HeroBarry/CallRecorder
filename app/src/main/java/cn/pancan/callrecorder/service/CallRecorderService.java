package cn.pancan.callrecorder.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import cn.pancan.callrecorder.helper.CallListener;

/**
 * Created by pan on 2016/6/27.
 */
public class CallRecorderService extends Service{
    @Override
    public void onCreate() {
        super.onCreate();
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(CallRecorderService.this.TELEPHONY_SERVICE);
        telephonyManager.listen(new CallListener(), PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

}
