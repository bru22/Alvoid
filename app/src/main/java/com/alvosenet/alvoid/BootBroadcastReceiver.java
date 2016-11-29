package com.alvosenet.alvoid;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by brucezeng on 11/9/2016.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    private JobScheduler mJobScheduler;
    @Override
    public void onReceive(Context context, Intent intent) {

        //l
        ConfigurationManager.loadConfiguration(context);
        if (ConfigurationManager.getIsLockScreen()) {
            mJobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

            ComponentName jobservice = new ComponentName(context.getPackageName(), OnTimeJobService.class.getName());

            JobInfo.Builder builder = new JobInfo.Builder(1, jobservice);
            builder.setPeriodic(60 * 1000); //1 min = 60 secs
            builder.setPersisted(true);

            if (mJobScheduler.schedule(builder.build()) <= 0) {
                Toast.makeText(context, "Schedule failed", Toast.LENGTH_SHORT).show();
            }
        }
//        Intent bootIntent = new Intent(context,MainActivity.class);
//        bootIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        context.startActivity(bootIntent);
    }
}
