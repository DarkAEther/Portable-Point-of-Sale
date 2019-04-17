package edu.pes.darkaether0x1.pos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Transactions extends AppCompatActivity {
    List<transContent> list;

    SQLiteDatabase db;
    String Transaction_TABLE = "transactions";
    //String Transaction_Item_TABLE = "transaction_items";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        db = openOrCreateDatabase("core.db", Context.MODE_PRIVATE,null);
        list = new ArrayList<transContent>();

        Cursor C = db.rawQuery("SELECT * FROM " + Transaction_TABLE, null);
        if(C.getCount() == 0) {
            Toast.makeText(Transactions.this, "No transactions happened!", Toast.LENGTH_LONG).show();
        }

        else {

            C = db.rawQuery("SELECT * FROM transactions JOIN transaction_items ON id = transaction_id" ,null);
            while (C.moveToNext()){

                transContent n = new transContent(C.getString(C.getColumnIndex("id")),C.getString(C.getColumnIndex("date")),C.getString(C.getColumnIndex("qty")),C.getString(C.getColumnIndex("amount")));
                list.add(n);
            }
            Transaction_Adapter transAdapter = new Transaction_Adapter(list , this); //provide string of item
            ListView listView = (ListView) findViewById(R.id.tran_list_id);
            listView.setAdapter(transAdapter); // create an adapter using cursor pointing at the data, provide the adapter here
            transAdapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getApplicationContext(),InvoiceActivity.class);
                    i.putExtra("transaction_id",list.get(position).getid());
                    startActivity(i);
                }
            });
        }



    }
}
