package edu.mobile.homework1.obj;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private String name;
    private Product product;
    private int amount;
    private boolean isChecked = false;

    public Cart(Product product, int amount){
        this.product = product; this.amount = amount;
    }
    public Cart(){}
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isChecked() { return isChecked; }

    public void setChecked(boolean isChecked){this.isChecked = isChecked;}

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("product",product);
        result.put("amount",this.amount);
        result.put("isChecked", this.isChecked);
        return result;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
