package edu.mobile.homework1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.w3c.dom.Text;

import java.util.Map;

import edu.mobile.homework1.Adapter.CartListAdapter;
import edu.mobile.homework1.R;
import edu.mobile.homework1.obj.Cart;
import edu.mobile.homework1.util.util;

public class CartActivity extends AppCompatActivity {
    private ListView cart_ListView;
    private CartListAdapter listAdapter;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private Button buy_BTN;
    private Button home_BTN;
    private TextView totalPrice_TV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        cart_ListView = (ListView) findViewById(R.id.list);
        buy_BTN = (Button) findViewById(R.id.buy_BTN);
        home_BTN = (Button) findViewById(R.id.home_BTN);
        totalPrice_TV = (TextView) findViewById(R.id.totalPrice_TV);

        listAdapter = new CartListAdapter();
        cart_ListView.setAdapter(listAdapter);

        home_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buy_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PurchaseActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ref = database.getReference("User/").child(mUser.getUid()).child("Cart/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Cart cart = snap.getValue(Cart.class);
                    cart.setProduct(snap.getValue(Cart.class).getProduct());
                    listAdapter.addItem(cart);
                    listAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref = database.getReference("User/").child(mUser.getUid()).child("totalPrice");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalPrice_TV.setText("총 가격 : "+ snapshot.getValue(int.class) + "원");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout_menu){
            util.SignOut(mAuth, this);
        }
        return super.onOptionsItemSelected(item);
    }
}
