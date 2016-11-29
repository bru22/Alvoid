package com.alvosenet.alvoid;

import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.dubu.lockscreenusingservice.Lockscreen;
import com.github.dubu.lockscreenusingservice.SharedPreferencesUtil;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String Tag = "MainActivity";
    private Calendar calendar = Calendar.getInstance();
    private Context context;
    private Button startBtn;
    private TextView startTime;
    private TextView endTime;

    private JobScheduler mJobScheduler;
    private SwitchCompat mSwitchd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(Tag,"onCreate");
        context = this;
        startBtn = (Button)findViewById(R.id.StartBtn);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);

        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);


        //setContentView(R.layout.activity_main);
        SharedPreferencesUtil.init(context);

        mSwitchd = (SwitchCompat) this.findViewById(R.id.switch_locksetting);
        mSwitchd.setTextOn("yes");
        mSwitchd.setTextOff("no");
        boolean lockState = SharedPreferencesUtil.get(Lockscreen.ISLOCK);
        if (lockState) {
            mSwitchd.setChecked(true);

        } else {
            mSwitchd.setChecked(false);

        }

        mSwitchd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ConfigurationManager.setIsLockScreen(true);
//                    SharedPreferencesUtil.setBoolean(Lockscreen.ISLOCK, true);
//                    Lockscreen.getInstance(context).startLockscreenService();
                } else {
                    ConfigurationManager.setIsLockScreen(false);
                    mJobScheduler.cancel(1);
//                    SharedPreferencesUtil.setBoolean(Lockscreen.ISLOCK, false);
//                    Lockscreen.getInstance(context).stopLockscreenService();
                }

            }
        });

        //Load Congfig from Config file
        ConfigurationManager.loadConfiguration(context);
        //Register broadcastreceiver
        //LocalBroadcastManager.getInstance(context).registerReceiver(lockscreenReceiver,new IntentFilter("com.alvosenet.alvoid.JobLockScreen"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag, "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mJobScheduler.cancel(1);
    }
//    private BroadcastReceiver lockscreenReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context ctx, Intent intent) {
//            Log.d(Tag,"LockScreenReceiver onReceive");
//            Boolean isLock = intent.getBooleanExtra(Lockscreen.ISLOCK,false);
//            Toast.makeText(getApplicationContext(),"isLock = "+ isLock , Toast.LENGTH_SHORT).show();
//            if(isLock){
//                SharedPreferencesUtil.setBoolean(Lockscreen.ISLOCK, true);
//                Lockscreen.getInstance(context).startLockscreenService();
//            }else{
//                SharedPreferencesUtil.setBoolean(Lockscreen.ISLOCK, false);
//                Lockscreen.getInstance(context).stopLockscreenService();
//            }
//
//        }
//    };

    public void onClick(View view){
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        switch (view.getId()){
            case R.id.StartBtn:
                Log.v(Tag,"Click Start Button");
                TimePickerDialog startTimePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                startTime.setText(format(hourOfDay)+":"+format(minute));
                                ConfigurationManager.setStartTime(format(hourOfDay)+format(minute));

                                //calendar.set();
                            }
                        }, hour, minute, true);
                startTimePickerDialog.show();

                break;
            case R.id.EndBtn:
                Log.v(Tag,"Click End Button");
                TimePickerDialog endTimePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                endTime.setText(format(hourOfDay)+":"+format(minute));
                                ConfigurationManager.setEndTime(format(hourOfDay)+format(minute));
                            }
                        }, hour, minute, true);
                endTimePickerDialog.show();
                break;
            case R.id.Set:
                Log.v(Tag,"Click Set Button");
                if (ConfigurationManager.getIsLockScreen()) {
                    ComponentName jobservice = new ComponentName(getPackageName(), OnTimeJobService.class.getName());

                    JobInfo.Builder builder = new JobInfo.Builder(1, jobservice);
                    builder.setPeriodic(60 * 1000); //1 min = 60 secs
                    builder.setPersisted(true);

                    if (mJobScheduler.schedule(builder.build()) <= 0) {
                        Toast.makeText(context, "Schedule failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please switch \"ON\" to eable lockscreen config", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Cancel:
                Log.v(Tag,"Click Cancel Button");
                mJobScheduler.cancel(1);
                break;
        }
    }

    private String format(int x) {
        String s = String.valueOf(x);
        return (s.length() == 1)?"0" + s:s;
    }
//    private class MyOnTimeSetListener implements TimePickerDialog.OnTimeSetListener{
//        @Override
//        public void onTimeSet(TimePicker view, int hour, int minute) {
//            switch (view.getId()){
//                case R.id.startTime:
//                    startTime.setText(hour+":"+minute);
//                    break;
//                case R.id.endTime:
//                    endTime.setText(hour+":"+minute);
//                    break;
//            }
//
//        }
//    }
}
