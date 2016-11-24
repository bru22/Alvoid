package com.alvosenet.alvoid;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by brucezeng on 11/7/2016.
 */

public class OnTimeJobService extends JobService {

    private static final String Tag = "OnTimeJobService";
    private Handler mJobHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            //int n = new Random().nextInt(100);
            //Toast.makeText(getApplicationContext(),"JobService task running: "+n , Toast.LENGTH_SHORT).show();

            //CheckTime
            //Get Current Time
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);

            String currentTime = Integer.toString(hour) + Integer.toString(min);
            String startTime = ConfigurationManager.getStartTime();
            String endTime = ConfigurationManager.getEndTime();

            //Check whether cross day
            boolean crossDay = endTime.compareTo(startTime) < 0;
            boolean inTime = false;
            if(!crossDay){
                if((currentTime.compareTo(startTime) >=0) && (endTime.compareTo(currentTime) >=0)){
                    inTime = true;
                }
            }else{
                if( (startTime.compareTo(currentTime) <=0) || (currentTime.compareTo(endTime) <=0) ){
                    inTime = true;
                }
            }
            Toast.makeText(getApplicationContext(),"inTime = "+ inTime , Toast.LENGTH_SHORT).show();


            jobFinished((JobParameters) message.obj , false);
            return true;
        }
    });

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        mJobHandler.sendMessage(Message.obtain(mJobHandler ,1,jobParameters));

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        mJobHandler.removeMessages(1);
        return true;
    }
}
