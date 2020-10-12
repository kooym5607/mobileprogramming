package edu.mobile.homework1.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.mobile.homework1.R;

import static edu.mobile.homework1.util.textFilter.*;

public class SignupActivity extends AppCompatActivity {
    private EditText       email_ET;
    private EditText    password_ET;
    private Button finishSignup_BTN;

    private FirebaseAuth        mAuth;
    private FirebaseUser         user;
    private FirebaseDatabase database;
    private DatabaseReference     ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //타이틀바 숨기기
        setTheme(R.style.noActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email_ET = (EditText)findViewById(R.id.email_ET);
        password_ET = (EditText)findViewById(R.id.password_ET);

        //비밀번호 필터 적용
        password_ET.setFilters(new InputFilter[]{filterAlphaNum});

        finishSignup_BTN = (Button)findViewById(R.id.finishSignup_BTN);
        finishSignup_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(email_ET.getText().toString(), password_ET.getText().toString());
            }
        });
    }

    private void signUp(String email, String password){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("User/");

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser();

                            // 가입되는 즉시 DB에 회원정보 저장.
                            ref.child(user.getUid()).setValue(user.getEmail());
                            mAuth.signOut();
                            SignupActivity.this.finish();
                        }
                        else{
                            Toast.makeText(SignupActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}