package edu.mobile.homework1.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
    private ArrayList<Cart> cartArrayList = new ArrayList<>();
    private Product product;
    private FirebaseDatabase database= FirebaseDatabase.getInstance();
    private DatabaseReference ref;
    private int totalPrice = 0;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser;
    private int price;

    class CustomViewHolder{
        ImageView icon_IV;
        TextView productName_TV;
        TextView productPrice_TV;
        TextView productAmount_TV;
        CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        View view = convertView;
        final CustomViewHolder holder;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_cart, parent, false);
            mUser = mAuth.getCurrentUser();
            ref = database.getReference("User/").child(mUser.getUid()).child("totalPrice");
            ref.setValue(totalPrice);

            holder = new CustomViewHolder();
            holder.icon_IV = (ImageView) view.findViewById(R.id.icon_IV);
            holder.productAmount_TV = (TextView) view.findViewById(R.id.productAmount_TV);
            holder.productName_TV = (TextView) view.findViewById(R.id.productName_TV);
            holder.productPrice_TV = (TextView) view.findViewById(R.id.productPrice_TV);
            holder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);

            ref = database.getReference("User/").child(mUser.getUid()).child("totalPrice");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    totalPrice = snapshot.getValue(int.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            view.setTag(holder);

            final Cart cart = cartArrayList.get(pos);
            cart.setProduct(cartArrayList.get(pos).getProduct());
            ref = database.getReference("Product/").child(cart.getProduct().getName());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        product = snapshot.getValue(Product.class);
                        holder.icon_IV.setImageDrawable(ContextCompat.getDrawable(context, product.getIconId()));
                        holder.productName_TV.setText(product.getName());
                        holder.productPrice_TV.setText(Integer.toString(product.getPrice()) + "원");
                        holder.productAmount_TV.setText(Integer.toString(cart.getAmount()) + "개");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            holder.checkBox.setChecked(cart.isChecked());

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.checkBox.isChecked()){
                        cart.setChecked(true);
                        totalPrice += cart.getProduct().getPrice() * cart.getAmount();
                        Log.e("PRICE : ", Integer.toString(totalPrice));
                    }
                    else{
                        cart.setChecked(false);
                        totalPrice -= cart.getProduct().getPrice() * cart.getAmount();
                        Log.e("PRICE : ", Integer.toString(totalPrice));
                    }
                    ref = database.getReference("User/").child(mUser.getUid()).child("Cart/").child(cart.getProduct().getName()).child("isChecked");
                    ref.setValue(cart.isChecked());
                    ref = database.getReference("User/").child(mUser.getUid()).child("totalPrice");
                    ref.setValue(totalPrice);
                }
            });
        }

        return view;
    }

    @Override
    public int getCount() {
        return cartArrayList.size();
    }

    @Override
    public Cart getItem(int position) {
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
