package cn.pancan.callrecorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.pancan.callrecorder.service.CallRecorderService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent serviceIntent=new Intent(MainActivity.this, CallRecorderService.class);
        startService(serviceIntent);
    }
}
