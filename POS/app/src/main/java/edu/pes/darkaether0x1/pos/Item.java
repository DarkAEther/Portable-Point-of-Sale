package edu.pes.darkaether0x1.pos;

public class Item {
    String price;
    String name;
    String barcode;
    String qty;

    Item (String barcode, String name,String qty, String price) {
        this.barcode = barcode;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }
    public String getcode() {
        return this.barcode;
    }
    public String getqty() {
        return this.qty;
    }
    public String getname() {
        return this.name;
    }
    public String getprice() {
        return this.price;
    }

}
