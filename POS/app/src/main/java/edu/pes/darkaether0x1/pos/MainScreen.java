package edu.pes.darkaether0x1.pos;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreen extends AppCompatActivity {

    Button tosale,tostock,totransactions,toorder,toinventory,tosupplier;
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
        db = openOrCreateDatabase("core.db", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS 'items' ( 'code' TEXT NOT NULL, 'name' TEXT NOT NULL, 'qty' INTEGER NOT NULL DEFAULT 0, 'price' REAL, PRIMARY KEY('code'))");
        db.execSQL("CREATE TABLE IF NOT EXISTS 'suppliers' ( 'id' INTEGER PRIMARY KEY , 'name' TEXT NOT NULL, 'email' TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS  \"transactions\" ( 'id' INTEGER PRIMARY KEY AUTOINCREMENT, 'amount' REAL, 'paymethod' TEXT, 'date' TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS 'transaction_items' ( 'transaction_id' INTEGER, 'item_id' TEXT, FOREIGN KEY('transaction_id') REFERENCES 'transactions'('id'))");
        db.execSQL("CREATE TABLE IF NOT EXISTS 'supplier_item' ( 'supplier_id' INTEGER, 'item_id' TEXT, FOREIGN KEY('supplier_id') REFERENCES 'suppliers'('id'))");

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

            }
        });
        totransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        toorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
