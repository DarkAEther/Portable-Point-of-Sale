package edu.pes.darkaether0x1.pos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivityS7 extends AppCompatActivity {
    SQLiteDatabase db;
    Spinner spin;
    EditText code;
    EditText qty;
    TextView sup;
    FloatingActionButton send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s7);


        db = openOrCreateDatabase("core.db", Context.MODE_PRIVATE,null);
        spin = findViewById(R.id.supplier_spinner);
        code = findViewById(R.id.reorder_item_code);
        qty = findViewById(R.id.reorder_item_qty);
        sup = findViewById(R.id.reorder_sup);
        send = findViewById(R.id.send_order);

        final ArrayList<String> arrayList=new ArrayList<String>();
        // Create an ArrayAdapter using the string array and a default spinner layout
        // Specify the layout to use when the list of choices appears
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spin.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.getText().toString().equals("") || qty.getText().toString().equals("")|| sup.getText().toString().equals("")){
                    showMessage("Insufficient Data","Please fill all the required data");
                    return;
                }
                Cursor email = db.rawQuery("SELECT email FROM suppliers WHERE id='"+spin.getSelectedItem().toString()+"'",null);
                if (email.moveToFirst()){
                    String address = email.getString(0);
                    //Email intent
                    Intent email_send = new Intent(Intent.ACTION_SEND);
                    email_send.setType("message/rfc822");
                    email_send.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
                    email_send.putExtra(Intent.EXTRA_SUBJECT,"Item Order");
                    email_send.putExtra(Intent.EXTRA_TEXT,"I would like to place an order for "+qty.getText().toString()+" items with the bar code "+code.getText().toString()+" Thank you");
                    startActivity(Intent.createChooser(email_send,"Select Email App"));
                }
            }
        });
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                arrayList.clear();
                Cursor c = db.rawQuery("SELECT supplier_id FROM supplier_item where item_id= '"+code.getText().toString().trim()+"'",null);
                String sup;
                while(c.moveToNext())
                {
                    sup=c.getString(0);
                    arrayList.add(sup);
                }
                adapter.notifyDataSetChanged();
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
