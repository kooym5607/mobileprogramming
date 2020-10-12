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
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;

    public ProductListAdapter(Context context){
        super();
        productArrayList = new ArrayList<Product>();
        for(int i=0;i<=10;i++){
            productAmount.put(i+"개", i);
            arrayList.add(i+"개");
        }
        mUser = mAuth.getCurrentUser();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        icon_IV.setImageDrawable(ContextCompat.getDrawable(context,product.getIconId()));
        productName_TV.setText(product.getName());
        productPrice_TV.setText(Integer.toString(product.getPrice()) + "원");


        ref = database.getReference("User/").child(mUser.getUid()).child("Cart/");
        productAmount_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cart cart = new Cart(product, position);
                cart.setChecked(false);
                cart.setName(product.getName());
                Map<String, Object> cartMap = cart.toMap();

                ref = database.getReference("User/").child(mUser.getUid()).child("Cart/").child(product.getName());
                ref.updateChildren(cartMap);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    public void addItem(int iconId, String name, int price){
        Product product = new Product(iconId,name, price);
        Map<String, Object> value = product.toMap();

        ref = database.getReference("Product/").child(name);
        ref.updateChildren(value);
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
