package cn.pancan.callrecorder.helper;

import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by pan on 2016/6/27.
 */
public class CallListener extends PhoneStateListener {
    private String number;
    private boolean isRecord;
    private MediaRecorder recorder ;

    public CallListener() {
        this.recorder = new MediaRecorder();
        //recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);//存储格式
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);//设置编码

    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        switch (state){
            case TelephonyManager.CALL_STATE_IDLE://空闲状态
                number = null;
                if (recorder != null && isRecord) {
                    recorder.stop();//停止录音
                    recorder.reset();
                    recorder.release();
                    isRecord = false;
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK://接听状态
                number=incomingNumber;
                String recordTitle= number+"_"+String.valueOf(System.currentTimeMillis());
                File fileDir=new File(Environment.getExternalStorageDirectory()+File.separator+"CallRecorder");
                if (!fileDir.exists())
                    fileDir.mkdirs();
                File file=new File(fileDir.getAbsolutePath(),recordTitle + ".amr");


                recorder.setOutputFile(file.getAbsolutePath());
                try {
                    recorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                recorder.start(); // 开始刻录
                isRecord = true;
                break;
            case TelephonyManager.CALL_STATE_RINGING://响铃:来电号码
                number=incomingNumber;
                break;
        }
    }
}
