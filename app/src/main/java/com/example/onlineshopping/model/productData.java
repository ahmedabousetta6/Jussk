package com.example.onlineshopping.model;

public class productData {

    String name;
    String color;
    String price;
    String barcode;
    byte[] image;


    public productData() {
    }

    public productData(String name, String color, String price,String barcode ,byte[] image) {
        this.name = name;
        this.color = color;
        this.price = price;
        this.barcode=barcode;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getbarcode(){return barcode;}
    public void setBarcode(String barcode){this.barcode=barcode;}

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
