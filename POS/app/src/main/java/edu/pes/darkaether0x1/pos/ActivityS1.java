package edu.pes.darkaether0x1.pos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ActivityS1 extends AppCompatActivity {

    Button scanbtn,proceedbtn,addqty,subqty,add_item;
    TextView total;
    EditText barcode,qty;
    ListView item_list;
    SQLiteDatabase db;
    Double gtot;
    List<Item> ListElements;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                barcode.setText(contents);

            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
                Toast.makeText(getApplicationContext(),"Barcode scanner error",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void showMessage(String title, String message){
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);
        alertbuilder.setTitle(title);
        alertbuilder.setMessage(message);
        alertbuilder.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s1);
        scanbtn = findViewById(R.id.scanbtn);
        proceedbtn = findViewById(R.id.salebtn);
        add_item = findViewById(R.id.add_item_sale);
        addqty = findViewById(R.id.addqty);
        subqty = findViewById(R.id.subqty);
        total = findViewById(R.id.total);
        barcode = findViewById(R.id.barcode);
        qty = findViewById(R.id.qty);
        item_list = findViewById(R.id.itemlist);
        gtot = 0.00;
        db = openOrCreateDatabase("core.db", Context.MODE_PRIVATE,null);

        ListElements = new ArrayList<Item>();

        final Sales_Adapter adapter = new Sales_Adapter(ListElements,this);
        item_list.setAdapter(adapter);

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.rawQuery("SELECT * FROM items WHERE code = '"+barcode.getText().toString().trim()+"'",null);
                String pr;
                String name;
                if (c.moveToFirst()){
                    pr = c.getString(3);
                    name = c.getString(1);
                }else{
                    showMessage("Error","This Item doesn't exist");
                    return;
                }

                Item a = new Item(barcode.getText().toString(),name,qty.getText().toString(),pr);
                gtot = Double.parseDouble(pr)*Double.parseDouble(qty.getText().toString()) + gtot;
                total.setText(gtot.toString());
                ListElements.add(a);
                adapter.notifyDataSetChanged();
                barcode.setText("");
                qty.setText("1");

            }
        });

        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activate barcode scanner to scan code
                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "PRODUCT_MODE"); // "PRODUCT_MODE for bar codes

                    startActivityForResult(intent, 0);

                } catch (Exception e) {

                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(marketIntent);
                }
            }
        });


        proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //perform all required processing
                Date c1 = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
                String formattedDate = df.format(c1);
                Integer random = new Random().nextInt(1000000);

                db.execSQL("INSERT INTO TRANSACTIONS VALUES ('"+ random.toString()+"','"+ gtot+"','cash','"+formattedDate+"');");

                for (Item it: ListElements) {
                    Cursor c = db.rawQuery("SELECT * FROM items WHERE code = '"+it.getcode()+"'",null);
                    if (c.moveToFirst()){
                        db.execSQL("INSERT INTO transaction_items VALUES ('"+random.toString()+"','"+it.getcode()+"');");
                    }else{
                        showMessage("Error","This Item doesn't exist " + it.getcode());
                    }
                }
                showMessage("Success","Sale Completed");
                finish();
            }
        });

        addqty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = Integer.parseInt(qty.getText().toString());
                i = i+1;
                qty.setText(i.toString());
            }
        });
        subqty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer i = Integer.parseInt(qty.getText().toString());
                i = i-1;
                qty.setText(i.toString());
            }
        });
    }
}
