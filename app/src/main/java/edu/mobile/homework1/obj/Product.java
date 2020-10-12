package edu.mobile.homework1.obj;

import android.graphics.drawable.Drawable;

public class Product {
    private Drawable iconDrawable;
    private String name;
    private int   price;
    private int iconId;

    public Product(int iconId, String name, int price){this.iconId=iconId; this.name = name; this.price = price;}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public Drawable getIcon() { return iconDrawable; }
    public void setIcon(Drawable iconDrawable) { this.iconDrawable = iconDrawable; }


    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
