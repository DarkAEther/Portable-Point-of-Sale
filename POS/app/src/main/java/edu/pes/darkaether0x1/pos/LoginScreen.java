package edu.pes.darkaether0x1.pos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginScreen extends AppCompatActivity {

    EditText uname,password;
    Button login;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        uname = findViewById(R.id.uname_login);
        password = findViewById(R.id.password_login);
        login = findViewById(R.id.loginbtn);

        db = openOrCreateDatabase("core.db", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS 'items' ( 'code' TEXT NOT NULL, 'name' TEXT NOT NULL, 'qty' INTEGER NOT NULL DEFAULT 0, 'price' REAL, PRIMARY KEY('code'))");
        db.execSQL("CREATE TABLE IF NOT EXISTS 'suppliers' ( 'id' INTEGER PRIMARY KEY , 'name' TEXT NOT NULL, 'email' TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS  \"transactions\" ( 'id' INTEGER PRIMARY KEY, 'amount' REAL, 'paymethod' TEXT, 'date' TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS 'transaction_items' ( 'transaction_id' INTEGER, 'item_id' TEXT, FOREIGN KEY('transaction_id') REFERENCES 'transactions'('id'))");
        db.execSQL("CREATE TABLE IF NOT EXISTS 'supplier_item' ( 'supplier_id' INTEGER, 'item_id' TEXT, FOREIGN KEY('supplier_id') REFERENCES 'suppliers'('id'))");


    }
}
