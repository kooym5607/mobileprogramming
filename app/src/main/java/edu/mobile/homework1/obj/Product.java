package edu.mobile.homework1.obj;

import android.graphics.drawable.Drawable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Product {
    private String name;
    private int   price;
    private int iconId;

    public Product(){}
    public Product(int iconId, String name, int price){this.iconId=iconId; this.name = name; this.price = price;}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }


    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name",name);
        result.put("price", price);
        result.put("iconId", iconId);

        return result;
    }
}
