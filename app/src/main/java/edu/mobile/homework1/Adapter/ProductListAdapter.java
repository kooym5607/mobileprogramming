package edu.mobile.homework1.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.mobile.homework1.R;
import edu.mobile.homework1.obj.Cart;
import edu.mobile.homework1.obj.Product;

public class ProductListAdapter extends BaseAdapter {
    private ArrayList<Product> productArrayList;
    ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    private HashMap<String, Integer> productAmount = new HashMap<String, Integer>();

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    public ProductListAdapter(Context context){
        super();
        productArrayList = new ArrayList<Product>();
        for(int i=0;i<=10;i++){
            productAmount.put(i+"개", i);
            arrayList.add(i+"개");
        }
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,arrayList);

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_product,parent,false);
        }


        ImageView icon_IV = (ImageView) view.findViewById(R.id.icon_IV);
        TextView productName_TV = (TextView) view.findViewById(R.id.productName_TV);
        TextView productPrice_TV = (TextView) view.findViewById(R.id.productPrice_TV);
        final Spinner productAmount_Spinner = (Spinner) view.findViewById(R.id.productAmount_Spinner);
        productAmount_Spinner.setAdapter(arrayAdapter);
        final Product product = productArrayList.get(position);

        /**
         * Todo 스피너 default 값 DB에서 읽어와서 지정하기.
         */
        final Map<String, Object> cart = new HashMap<>();
        final GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {};

        ref = database.getReference("User/").child(mUser.getUid()).child("Cart/").child(product.getName());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final int amount = snapshot.getValue(Integer.class);
                new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {
                        productAmount_Spinner.setSelection(amount);
                    }
                }, 100);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        icon_IV.setImageDrawable(ContextCompat.getDrawable(context,product.getIconId()));
        productName_TV.setText(product.getName());
        productPrice_TV.setText(Integer.toString(product.getPrice()) + "원");

        ref = database.getReference("User/").child(mUser.getUid()).child("Cart/");
        productAmount_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cart.put(product.getName(), productAmount.get(productAmount_Spinner.getItemAtPosition(position)));
                ref.updateChildren(cart);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    public void addItem(int iconId, String name, int price){
        Product product = new Product(iconId,name, price);

        ref = database.getReference("Product/").child(name);
        ref.setValue(product);
        productArrayList.add(product);
    }

    @Override
    public int getCount() {
        return productArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return productArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




}
