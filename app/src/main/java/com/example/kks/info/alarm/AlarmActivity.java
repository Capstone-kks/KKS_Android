package com.example.kks.info.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kks.R;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity{

    private Switch alarm_switch;
    private TextView time_tv;
    private Button time_btn, ok_btn, cancel_btn;
    private TimePicker timePicker;
    private LinearLayout time_layout;

    private boolean alarm_status, time_change;
    private int hour, mminute;
    private String alarm_time;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private PackageManager pm;
    private ComponentName receiver;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        receiver = new ComponentName(getApplicationContext(), AlarmBroadcastReceiver.class);
        pm = getPackageManager();

        alarm_switch = findViewById(R.id.alarm_swith);
        time_tv = findViewById(R.id.alarm_time_tv);
        time_btn = findViewById(R.id.alarm_time_btn);
        ok_btn = findViewById(R.id.alarm_ok_btn);
        cancel_btn = findViewById(R.id.alarm_cancel_btn);
        timePicker = findViewById(R.id.alarm_tp);
        time_layout = findViewById(R.id.alarm_time_layout);

        //알람 설정 정보 가져오기
        preferences  = getSharedPreferences("AlarmInfo", MODE_PRIVATE);
        alarm_status = preferences.getBoolean("AlarmStatus",false);

        //알람 on off에 따라 초기화면 설정
        if(alarm_status) {
            alarm_time = preferences.getString("Time","");
            hour = preferences.getInt("Hour",0);
            mminute = preferences.getInt("Minute",0);
            alarm_switch.setChecked(true);
            time_tv.setEnabled(true);
            time_btn.setEnabled(true);
            time_btn.setText(alarm_time);
        }
        else{
            alarm_switch.setChecked(false);
            time_tv.setEnabled(false);
            time_btn.setEnabled(false);
            time_btn.setText("");
        }

        //알람 스위치 버튼 클릭 시
        alarm_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    time_tv.setEnabled(true);
                    time_btn.setEnabled(true);
                    time_btn.setText("-- : --");
                }
                else{
                    time_tv.setEnabled(false);
                    time_btn.setEnabled(false);
                    time_btn.setText("");

                    if (pendingIntent != null && alarmManager != null) {
                        alarmManager.cancel(pendingIntent);
                        pm.setComponentEnabledSetting(receiver,
                                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                PackageManager.DONT_KILL_APP);
                    }
                }
            }
        });

        //시간 버튼 클릭 시 TimePicker 레이아웃 보이기
        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_layout.setVisibility(View.VISIBLE);
            }
        });

        //TimePicker 시간 변경 시
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                alarm_time = String.format("%02d : %02d",hourOfDay,minute);
                hour = hourOfDay;
                mminute = minute;
                time_btn.setText(alarm_time);



            }
        });

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"알림 설정 완료",Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("AlarmStatus",true);
                editor.putString("Time",alarm_time);
                editor.putInt("Hour",hour);
                editor.putInt("Minute",mminute);
                editor.commit();

                //알람설정
                alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                Intent ReceiverIntent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, ReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.DAY_OF_WEEK,1);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, mminute);

                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                pm.setComponentEnabledSetting(receiver,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP);


                time_layout.setVisibility(View.GONE);
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_layout.setVisibility(View.GONE);
            }
        });
    }
}
