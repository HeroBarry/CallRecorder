package cn.pancan.callrecorder.helper;

import android.telephony.PhoneStateListener;

/**
 * Created by Administrator on 2016/6/27.
 */
public class CallListener extends PhoneStateListener {
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
    }
}
