package edu.pes.darkaether0x1.pos;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainScreen extends AppCompatActivity {

    Button tosale,tostock,totransactions,toorder,toinventory,tosupplier,no;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        tosale = findViewById(R.id.goto_sale);
        tostock = findViewById(R.id.goto_itemmgmt);
        totransactions = findViewById(R.id.goto_transact);
        toorder = findViewById(R.id.goto_order);
        toinventory = findViewById(R.id.goto_inventory);
        tosupplier = findViewById(R.id.goto_supp);
        //no = findViewById(R.id.notif);
        db = openOrCreateDatabase("core.db", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS 'items' ( 'code' TEXT NOT NULL, 'name' TEXT NOT NULL, 'qty' INTEGER NOT NULL DEFAULT 0, 'price' REAL, PRIMARY KEY('code'))");
        db.execSQL("CREATE TABLE IF NOT EXISTS 'suppliers' ( 'id' TEXT PRIMARY KEY , 'name' TEXT NOT NULL, 'email' TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS  \"transactions\" ( 'id' TEXT PRIMARY KEY, 'amount' REAL, 'paymethod' TEXT, 'date' TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS 'transaction_items' ( 'transaction_id' TEXT, 'item_id' TEXT, 'qty' INTEGER, FOREIGN KEY('transaction_id') REFERENCES 'transactions'('id'))");
        db.execSQL("CREATE TABLE IF NOT EXISTS 'supplier_item' ( 'supplier_id' TEXT, 'item_id' TEXT, FOREIGN KEY('supplier_id') REFERENCES 'suppliers'('id'))");
        setNotificationAlarm(getApplicationContext());

        tosupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ActivityS6.class);
                startActivity(i);
            }
        });
        tosale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ActivityS1.class);
                startActivity(i);
            }
        });
        tostock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ActivityS5.class);
                startActivity(i);
            }
        });
        toinventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ActivityS4.class);
                startActivity(i);
            }
        });
        totransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Transactions.class);
                startActivity(i);
            }
        });
        toorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ActivityS7.class);
                startActivity(i);
            }
        });

    }


    private void setNotificationAlarm(Context context)
    {
        Intent intent = new Intent(getApplicationContext() , Sale_Service.class);
        PendingIntent pendingIntent  = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Set the alarm to start at 17:30 PM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 1);

// setRepeating() lets you specify a precise custom interval--in this case,
// 1 day
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
