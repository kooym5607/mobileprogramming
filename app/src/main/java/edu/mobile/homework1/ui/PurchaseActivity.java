package edu.mobile.homework1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.mobile.homework1.R;
import edu.mobile.homework1.obj.Cart;
import edu.mobile.homework1.util.util;

public class PurchaseActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;

    private GridLayout grid;
    private Button finish_BTN;

    private TextView totalPrice_TV;
    private TextView name1;
    private TextView name2;
    private TextView name3;
    private TextView price1;
    private TextView price2;
    private TextView price3;
    private TextView amount1;
    private TextView amount2;
    private TextView amount3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        mUser = mAuth.getCurrentUser();

        finish_BTN = (Button) findViewById(R.id.finish_BTN);
        finish_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseActivity.class);
                startActivity(intent);
                finish();
            }
        });
        totalPrice_TV = (TextView)findViewById(R.id.totalPrice_TV);
        name1 = (TextView)findViewById(R.id.name1);
        name2 = (TextView)findViewById(R.id.name2);
        name3 = (TextView)findViewById(R.id.name3);
        price1 = (TextView)findViewById(R.id.price1);
        price2 = (TextView)findViewById(R.id.price2);
        price3 = (TextView)findViewById(R.id.price3);
        amount1 = (TextView)findViewById(R.id.amount1);
        amount2 = (TextView)findViewById(R.id.amount2);
        amount3 = (TextView)findViewById(R.id.amount3);

        ref = database.getReference("User/").child(mUser.getUid()).child("totalPrice");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalPrice_TV.setText("총 가격 : "+Integer.toString(snapshot.getValue(int.class)) + "원");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        grid = (GridLayout) findViewById(R.id.grid);

        ref = database.getReference("User").child(mUser.getUid()).child("Cart/").child("eraser");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Cart cart = snapshot.getValue(Cart.class);
                amount1.setText(Integer.toString(cart.getAmount())+"개");
                price1.setText(Integer.toString(cart.getProduct().getPrice()));
                name1.setText(cart.getProduct().getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref = database.getReference("User").child(mUser.getUid()).child("Cart/").child("pen");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Cart cart = snapshot.getValue(Cart.class);
                amount2.setText(Integer.toString(cart.getAmount())+"개");
                price2.setText(Integer.toString(cart.getProduct().getPrice()));
                name2.setText(cart.getProduct().getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref = database.getReference("User").child(mUser.getUid()).child("Cart/").child("pencil");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Cart cart = snapshot.getValue(Cart.class);
                amount3.setText(Integer.toString(cart.getAmount())+"개");
                price3.setText(Integer.toString(cart.getProduct().getPrice()));
                name3.setText(cart.getProduct().getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




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
