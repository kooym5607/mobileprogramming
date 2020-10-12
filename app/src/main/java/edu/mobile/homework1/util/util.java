package edu.mobile.homework1.util;

import android.app.Activity;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

import edu.mobile.homework1.ui.MainActivity;

public class util {
    public static void SignOut(FirebaseAuth mAuth, Activity activity){
        mAuth.getInstance().signOut();
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}

