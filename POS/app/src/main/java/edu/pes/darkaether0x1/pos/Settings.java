package edu.pes.darkaether0x1.pos;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.FileInputStream;
import java.util.Calendar;

public class Settings extends AppCompatActivity {
    Button changetime;
    TextView time;
    int hour;
    int minute;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        changetime = findViewById(R.id.timebtn);
        time= findViewById(R.id.timeval);

         sp = getSharedPreferences("mypref",MODE_PRIVATE);
         editor = sp.edit();
        //time.setText(sp.getString("notiftime","17:30"));
        String[] stored = sp.getString("notiftime","17:30").split(":");
        hour = Integer.parseInt(stored[0]);
        minute = Integer.parseInt(stored[1]);
        updateTime(hour,minute);
        changetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdDialog(0).show();
            }
        });
    }
    protected Dialog createdDialog(int id) {
        switch (id) {
            case 0:
                return new TimePickerDialog(this, timePickerListener, hour, minute, false);
        }
        return null;
    }
    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
       int hr;
       int min;
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            hr = hourOfDay;
            min = minutes;
            updateTime(hr, min);
        }
    };
        private void updateTime(int hours, int mins) {
            editor.putString("notiftime",String.valueOf(hours)+":"+String.valueOf(mins));
            editor.commit();
            setNotificationAlarm(getApplicationContext());
            String timeSet = "";
            if (hours > 12) {
                hours -= 12;
                timeSet = "PM";
            } else if (hours == 0) {
                hours += 12;
                timeSet = "AM";
            } else if (hours == 12) timeSet = "PM";
            else timeSet = "AM";

            String minutes = "";

            if (mins < 10)
                minutes = "0" + mins;
            else
                minutes = String.valueOf(mins);

            String aTime = new StringBuilder().append(hours).append(':').append(minutes).append(" ").append(timeSet).toString();
            time.setText(aTime);
    }
    private void setNotificationAlarm(Context context)
    {
        Intent intent = new Intent(getApplicationContext() , Sale_Service.class);
        PendingIntent pendingIntent  = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        SharedPreferences sp = getSharedPreferences("mypref",MODE_PRIVATE);

        String[] stored = sp.getString("notiftime","17:30").split(":");
        int hour = Integer.parseInt(stored[0]);
        int minute = Integer.parseInt(stored[1]);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

// setRepeating() lets you specify a precise custom interval--in this case,
// 1 day
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
