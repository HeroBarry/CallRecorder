package cn.pancan.callrecorder.helper;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by pan on 2016/6/27.
 */
public class CallListener extends PhoneStateListener {
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        switch (state){
            case TelephonyManager.CALL_STATE_IDLE:
                System.out.println("挂断");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                System.out.println("接听");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                System.out.println("响铃:来电号码"+incomingNumber);
                break;
        }
    }
}
