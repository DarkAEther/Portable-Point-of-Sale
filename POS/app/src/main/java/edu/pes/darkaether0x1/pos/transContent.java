package edu.pes.darkaether0x1.pos;

public class transContent {

    String id;
    String date;
    String qty;
    String amount;

    transContent(String id, String date, String qty, String amount){
        this.id = id;
        this.date = date;
        this.qty = qty;
        this.amount = amount;
    }

    public String getid() {
        return this.id;
    }
    public String getdate() {
        return this.date;
    }
    public String getqty() {
        return this.qty;
    }
    public String getprice() {
        return this.amount;
    }
}

