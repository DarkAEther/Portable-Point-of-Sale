package edu.pes.darkaether0x1.pos;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.Normalizer2;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityS5 extends AppCompatActivity {

    Button scan,add,delete,modify,view;
    EditText code, name, qty, suppliers,price;
    SQLiteDatabase db;
    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                code.setText(contents);

            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
                Toast.makeText(getApplicationContext(),"Barcode scanner error",Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s5);

        scan = findViewById(R.id.scan_invent_btn);
        add = findViewById(R.id.additem);
        delete = findViewById(R.id.deleteitem);
        modify = findViewById(R.id.modifyitem);
        view = findViewById(R.id.viewitem);

        code = findViewById(R.id.barcode_invent);
        name = findViewById(R.id.name);
        qty = findViewById(R.id.stock);
        suppliers = findViewById(R.id.supplier);
        price = findViewById(R.id.price_invent);

        db = openOrCreateDatabase("core.db",Context.MODE_PRIVATE,null);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.getText().toString().equals("") || name.getText().toString().equals("") || qty.getText().toString().equals("") || price.getText().equals("") || suppliers.getText().toString().equals("")){
                    showMessage("Insufficient Data","Please enter all the required data");
                    return;
                }

                Cursor c = db.rawQuery("SELECT * from items WHERE code='" + code.getText().toString() + "'", null);
                if (!c.moveToFirst()) {
                    db.execSQL("INSERT INTO items VALUES('"+code.getText().toString()+"','"+name.getText().toString()+"','"+qty.getText().toString()+"','"+price.getText().toString()+"');");
                    String[] suppliertext = suppliers.getText().toString().split(",");
                    Cursor find_suppliers;
                    for (String supp:suppliertext) {
                        find_suppliers = db.rawQuery("SELECT * FROM suppliers WHERE id='"+supp+"'",null);
                        if (find_suppliers.moveToFirst()) {
                            db.execSQL("INSERT INTO supplier_item VALUES ('" + supp + "','" + code.getText().toString() + "');");
                        }
                    }

                    showMessage("Success","New Item added to Inventory");
                    code.setText("");
                    price.setText("");
                    name.setText("");
                    suppliers.setText("");
                    qty.setText("");
                }else{
                    showMessage("Error","Item exists");
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.getText().toString().equals("")){
                    showMessage("Insufficient Data","Please enter all the required data");
                    return;
                }
                Cursor c = db.rawQuery("SELECT * from items WHERE code='" + code.getText().toString() + "'", null);
                Cursor c1 = db.rawQuery("SELECT * from supplier_item WHERE item_id='" +code.getText().toString()+"'",null);
                if (c.moveToFirst()) {
                    price.setText(c.getString(3));
                    name.setText(c.getString(1));
                    qty.setText(c.getString(2));
                    String supps="";
                    while (c1.moveToNext()){
                        supps+=c1.getString(0)+",";
                    }
                    suppliers.setText(supps);
                }else{
                    showMessage("Error","Item not found");
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.getText().toString().equals("")){
                    showMessage("Insufficient Data","Please enter all the required data");
                    return;
                }
                Cursor c = db.rawQuery("SELECT * from items WHERE code='" + code.getText().toString() + "'", null);
                if (c.moveToFirst()) {
                    db.execSQL("DELETE FROM items WHERE code = '"+code.getText().toString().trim()+"';");
                    db.execSQL("DELETE FROM supplier_item WHERE item_id = '"+code.getText().toString().trim()+"';");
                    showMessage("Success","Item has been deleted");
                    code.setText("");
                    price.setText("");
                    name.setText("");
                    suppliers.setText("");
                    qty.setText("");
                }else{
                    showMessage("Error","Records not Updated");
                }
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.getText().toString().equals("")){
                    showMessage("Insufficient Data","Please enter all the required data");
                    return;
                }
                Cursor c = db.rawQuery("SELECT * from items WHERE code='" + code.getText().toString() + "'", null);
                if (c.moveToFirst()) {
                    db.execSQL("UPDATE items SET name='" + name.getText().toString().trim() + "',qty='"+qty.getText().toString().trim()+"',price='"+price.getText().toString()+"' WHERE code = '" + code.getText().toString().trim() + "';");
                    db.execSQL("DELETE FROM supplier_item WHERE item_id='"+code.getText().toString()+"';");

                    String[] suppliertext = suppliers.getText().toString().split(",");

                    for (String supp:suppliertext) {
                        db.execSQL("INSERT INTO supplier_item VALUES ('"+supp+"','"+code.getText().toString()+"');");
                    }

                    showMessage("Success","Record Updated");
                    code.setText("");
                    price.setText("");
                    name.setText("");
                    suppliers.setText("");
                    qty.setText("");
                }else{
                    showMessage("Error","Records not Updated");
                }
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
