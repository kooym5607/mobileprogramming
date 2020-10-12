package edu.mobile.homework1.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import edu.mobile.homework1.R;

import static edu.mobile.homework1.util.textFilter.*;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = ".MainActivity";

    private EditText email_ET;
    private EditText password_ET;
    private Button login_BTN;
    private Button signup_BTN;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //타이틀바 숨기기
        setTheme(R.style.noActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        email_ET = (EditText)findViewById(R.id.email_ET);
        password_ET = (EditText)findViewById(R.id.password_ET);
        password_ET.setFilters(new InputFilter[]{filterAlphaNum});
        login_BTN = (Button)findViewById(R.id.login_BTN);
        signup_BTN = (Button)findViewById(R.id.signup_BTN);

        login_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(email_ET.getText().toString(),password_ET.getText().toString());
            }
        });
        signup_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"로그인 성공");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                        else{
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(MainActivity.this,"존재하지 않는 id 입니다." ,Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(MainActivity.this,"비밀번호가 틀립니다." ,Toast.LENGTH_SHORT).show();
                            } catch (FirebaseNetworkException e) {
                                Toast.makeText(MainActivity.this,"Firebase NetworkException" ,Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this,"Exception" ,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //자동로그인. 이미 로그인이 된 상태라면 로그인화면을 스킵.
        if(currentUser!=null)
            updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user){
        if(user!=null){
            Intent intent = new Intent(this, ChooseActivity.class);
            startActivity(intent);
            finish();
        }
    }
}