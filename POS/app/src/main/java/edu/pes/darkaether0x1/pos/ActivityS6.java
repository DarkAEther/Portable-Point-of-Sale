package edu.pes.darkaether0x1.pos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityS6 extends AppCompatActivity {

    EditText id,name,email;
    Button add,del,mod,view;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s6);
        id = findViewById(R.id.supp_id);
        name = findViewById(R.id.supp_name);
        email = findViewById(R.id.supp_email);

        add = findViewById(R.id.add_supp);
        del = findViewById(R.id.del_supp);
        mod = findViewById(R.id.mod_supp);
        view = findViewById(R.id.view_supp);
        db = openOrCreateDatabase("core.db", Context.MODE_PRIVATE,null);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.rawQuery("SELECT * from suppliers WHERE id='" + id.getText().toString() + "'", null);
                if (c.moveToFirst()) {

                    showMessage("Error","Supplier Exists");
                }else{
                    db.execSQL("INSERT INTO suppliers VALUES('"+id.getText().toString()+"','"+name.getText().toString()+"','"+email.getText().toString()+"');");
                    showMessage("Success","Record Inserted");
                    id.setText("");
                    name.setText("");
                    email.setText("");
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.rawQuery("SELECT * from suppliers WHERE id='" + id.getText().toString() + "'", null);
                if (c.moveToFirst()) {
                    name.setText(c.getString(1));
                    email.setText(c.getString(2));
                }else{
                    showMessage("Error","Supplier Doesn't Exist");
                }
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.rawQuery("SELECT * from suppliers WHERE id='" + id.getText().toString() + "'", null);
                if (c.moveToFirst()) {
                    db.execSQL("DELETE FROM suppliers WHERE id = '"+id.getText().toString().trim()+"';");
                    db.execSQL("DELETE FROM supplier_item WHERE supplier_id = '"+id.getText().toString().trim()+"';");
                    showMessage("Success","Supplier Removed");
                    id.setText("");
                    name.setText("");
                    email.setText("");
                }else{
                    showMessage("Error","Records not Updated");
                }
            }
        });

        mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.rawQuery("SELECT * from suppliers WHERE id='" + id.getText().toString() + "'", null);
                if (c.moveToFirst()) {
                    db.execSQL("UPDATE suppliers SET name='" + name.getText().toString().trim() + "',email='"+email.getText().toString().trim()+"' WHERE id = '" + id.getText().toString().trim() + "';");
                    showMessage("Success","Record Updated");
                    id.setText("");
                    name.setText("");
                    email.setText("");
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
