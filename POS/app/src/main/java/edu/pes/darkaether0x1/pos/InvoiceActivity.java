package edu.pes.darkaether0x1.pos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class InvoiceActivity extends AppCompatActivity {
    WebView wv,mypage;
    FloatingActionButton fab;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        wv = findViewById(R.id.invoice_view);
        fab = findViewById(R.id.fab_print);
        db = openOrCreateDatabase("core.db",MODE_PRIVATE,null);
        Intent i = getIntent();
        String transaction_id = i.getStringExtra("transaction_id");

        Cursor c = db.rawQuery("SELECT * FROM transaction_items JOIN items ON item_id = code WHERE transaction_id='" + transaction_id+"'",null);
        Cursor c1 = db.rawQuery("SELECT * FROM transactions WHERE id='"+transaction_id+"'",null);
        Cursor c2 ;
        if (!c1.moveToFirst()){
            showMessage("Error","Transaction doesn't exist");
        }else{
            String Date = "Date: " + c1.getString(3);
            String html = "<html><style>table, th, td {\n" +
                    "  border: 1px solid black;\n" +
                    "  border-collapse: collapse;\n" +
                    "}</style><body><div style='text-align:center' ><h3 style='text-align:center'>Invoice</h3><p>"+Date+"</p><p>Transaction ID: "+transaction_id+"</p></div><br><h4>Items:</h4><div><table style='width:100%;'><tr><th>Item Code</th><th>Item Name</th><th>Quantity</th><th>Unit Cost</th></tr>";
            String itemrow;
            if (c.moveToFirst()) {
                itemrow = "<tr><td>"+c.getString(1)+"</td>";
                c2 = db.rawQuery("SELECT * FROM items WHERE code ='"+c.getString(1)+"'",null);
                c2.moveToFirst();
                itemrow+= "<td>"+c2.getString(1)+"</td>";
                itemrow+="<td>"+c.getString(2)+"</td>";
                itemrow+="<td>"+c2.getString(3)+"</td></tr>";
                html+=itemrow;
                while (c.moveToNext()){
                    itemrow = "<tr><td>"+c.getString(1)+"</td>";
                    c2 = db.rawQuery("SELECT * FROM items WHERE code ='"+c.getString(1)+"'",null);
                    c2.moveToFirst();
                    itemrow+= "<td>"+c2.getString(1)+"</td>";
                    itemrow+="<td>"+c.getString(2)+"</td>";
                    itemrow+="<td>"+c2.getString(3)+"</td></tr>";
                    html+=itemrow;
                }
                html+="</table><br><h5 style='text-align:right'>Grand Total: "+c1.getString(1)+"</h5><h5 style='text-align:right'>Payment Method: "+c1.getString(2)+"</h5></div></body></html>";
                wv.loadData(html, "text/html; charset=utf-8", "UTF-8");

            }else{
                showMessage("Error","This transaction didn't happen");
            }
        }
        mypage = wv;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //perform print
                print_page(mypage);
            }
        });
    }
    private void print_page(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);

        String jobName = getString(R.string.app_name) + " Document";

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

        // Save the job object for later status checking
        //printJobs.add(printJob);
    }
    public void showMessage(String title, String message){
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);
        alertbuilder.setTitle(title);
        alertbuilder.setMessage(message);
        alertbuilder.show();
    }

}


