package edu.mobile.homework1.ui;

import android.content.Intent;
import android.os.Bundle;
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
        final GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        param.height = GridLayout.LayoutParams.WRAP_CONTENT;
        param.width =(grid.getWidth()/3);
        param.setGravity(Gravity.CENTER);


        ref = database.getReference("User").child(mUser.getUid()).child("Cart/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Cart cart = snap.getValue(Cart.class);
                    TextView name = new TextView(getApplicationContext());
                    name.setText(cart.getProduct().getName());
                    grid.addView(name,param);
                    TextView price = new TextView(getApplicationContext());
                    price.setText(Integer.toString(cart.getProduct().getPrice()) + "원");

                    grid.addView(price,param);
                    TextView amount = new TextView(getApplicationContext());
                    amount.setText(Integer.toString(cart.getAmount()) + "개");

                    grid.addView(amount,param);
                }
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
