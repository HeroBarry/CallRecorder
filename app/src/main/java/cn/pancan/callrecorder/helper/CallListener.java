package cn.pancan.callrecorder.helper;

import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by pan on 2016/6/27.
 */
public class CallListener extends PhoneStateListener implements MediaRecorder.OnInfoListener, MediaRecorder.OnErrorListener{
    private String number;
    private boolean isRecord;
    private MediaRecorder recorder ;

    public CallListener() {
        this.recorder = new MediaRecorder();
        //recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
//        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);//存储格式


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

                recorder.reset();
                recorder.setAudioSource(1);
                recorder.setOutputFormat(1);//存储格式
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);//设置编码
                recorder.setOutputFile(file.getAbsolutePath());
                recorder.setOnInfoListener(this);
                recorder.setOnErrorListener(this);

                try {
                    recorder.prepare();
                } catch (IOException e) {
                    Log.e("CallRecorder", "RecordService::onStart() IOException attempting recorder.prepare()\n");
                    recorder = null;
                    return;
                }
                recorder.start(); // 开始刻录
                isRecord = true;
                break;
            case TelephonyManager.CALL_STATE_RINGING://响铃:来电号码
                number=incomingNumber;
                break;
        }
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        Log.e("CallRecorder", "RecordService got MediaRecorder onError callback with what: " + what + " extra: " + extra);
        isRecord = false;
        mr.release();
    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        Log.i("CallRecorder", "RecordService got MediaRecorder onInfo callback with what: " + what + " extra: " + extra);
        isRecord = false;
    }
}
