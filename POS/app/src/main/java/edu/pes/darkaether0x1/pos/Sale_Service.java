package edu.pes.darkaether0x1.pos;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class Sale_Service extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        SQLiteDatabase db = context.openOrCreateDatabase("core.db",MODE_PRIVATE,null);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
        String formattedDate = df.format(c);

        Cursor cur = db.rawQuery("SELECT * FROM transactions WHERE date ='"+formattedDate+"'",null);
        Double total = 0.00;
        Double amt;
        while (cur.moveToNext()){
            amt = Double.parseDouble(cur.getString(1));
            total = total + amt;
        }
        create_notif(total,context);

    }

    public void create_notif(Double value,Context context){

        Notification.Builder notif = new Notification.Builder(context.getApplicationContext())
                .setContentTitle("Daily Sales")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("Your Sales for today amounted to: "+ value.toString());

        NotificationManager nman = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "0x1";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Why is this even needed, damnit",NotificationManager.IMPORTANCE_DEFAULT);
            nman.createNotificationChannel(channel);
            notif.setChannelId(channelId);
        }
        nman.notify(0,notif.build());
    }
}
