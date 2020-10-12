package edu.mobile.homework1.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import edu.mobile.homework1.Fragment.ProductFragment;
import edu.mobile.homework1.R;
import edu.mobile.homework1.util.util;

public class ChooseActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private ProductFragment productFragment;
    private FragmentTransaction transaction;
    private Button buy_BTN;
    private Button cart_BTN;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        mAuth = FirebaseAuth.getInstance();

        buy_BTN = (Button)findViewById(R.id.buy_BTN);
        cart_BTN = (Button)findViewById(R.id.cart_BTN);
        buy_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(this, PurchaseActivity.class);

            }
        });
        cart_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        fragmentManager = getSupportFragmentManager();
        productFragment = new ProductFragment();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container,productFragment,"productFragment");
        transaction.commit();
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