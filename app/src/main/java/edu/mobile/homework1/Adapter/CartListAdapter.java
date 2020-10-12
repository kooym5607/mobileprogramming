package edu.mobile.homework1.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.mobile.homework1.R;
import edu.mobile.homework1.obj.Cart;
import edu.mobile.homework1.obj.Product;

public class CartListAdapter extends BaseAdapter {
    private ArrayList<Cart> cartArrayList;
    private Product product;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    public CartListAdapter(Context context){
        super();
        cartArrayList = new ArrayList<Cart>();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_cart,parent,false);
        }

        ImageView icon_IV = (ImageView) view.findViewById(R.id.icon_IV);
        TextView productName_TV = (TextView) view.findViewById(R.id.productName_TV);
        TextView productPrice_TV = (TextView) view.findViewById(R.id.productPrice_TV);
        TextView productAmount_TV = (TextView) view.findViewById(R.id.productAmount_TV);

        ref = database.getReference("Product/");
        String name = cartArrayList.get(pos).getName();
        int amount = cartArrayList.get(pos).getAmount();

//        ref.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        icon_IV.setImageDrawable(ContextCompat.getDrawable(context,product.getIconId()));
//        productName_TV.setText(product.getName());
//        productPrice_TV.setText(Integer.toString(product.getPrice()) + "원");
//        productAmount_TV.setText(Integer.toString(amount));

        return view;
    }

    @Override
    public int getCount() {
        return cartArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void addItem(Cart cart){
        cartArrayList.add(cart);
    }


}
