package edu.pes.darkaether0x1.pos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityS4 extends AppCompatActivity {
    ListView listView;
    SQLiteDatabase db;
    ListView item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s4);


        db = openOrCreateDatabase("core.db", Context.MODE_PRIVATE,null);
        listView=(ListView)findViewById(R.id.listView);

        final ArrayList<Item> arrayList=new ArrayList<Item>();

        final listAdapter adapter1=  new listAdapter(arrayList,this);
        listView.setAdapter(adapter1);
        Cursor c = db.rawQuery("SELECT * FROM items ",null);
        String barcode;
        String qty;
        String name;
        String price;

        if(c.moveToFirst())
        {

            barcode=c.getString(0);
            qty=c.getString(2);
            name=c.getString(1);
            price=c.getString(3);
            Item n=new Item(barcode,name,qty,price);
            arrayList.add(n);
            while(c.moveToNext())
            {
                barcode=c.getString(0);
                qty=c.getString(2);
                name=c.getString(1);
                price=c.getString(3);
                n=new Item(barcode,name,qty,price);

                arrayList.add(n);


            }
        }
        adapter1.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),arrayList.get(position).getname(),Toast.LENGTH_LONG).show();
                String bcode = arrayList.get(position).getcode();
                String iname = arrayList.get(position).getname();
                String iqty = arrayList.get(position).getqty();
                String iprice = arrayList.get(position).getprice();
                showMessage("Item Details","Barcode: "+bcode+"\nName: "+iname+"\nQuantity in Stock: "+iqty+"\nUnit Price: "+iprice);

            }
        });



    }
    public void showMessage(String title, String message){
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);
        alertbuilder.setTitle(title);
        alertbuilder.setMessage(message);
        alertbuilder.show();
    }
}
