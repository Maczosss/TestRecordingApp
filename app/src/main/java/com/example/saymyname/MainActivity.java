package com.example.saymyname;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AuthUser currentUser = Amplify.Auth.getCurrentUser();
//
//        if(currentUser == null){
//            //go to login page
//            setContentView(R.layout.activity_main);
//        }
//        else{
//            //go to record page
//            setContentView(R.layout.activity_main);
//        }
        //old code, go to login page
        setContentView(R.layout.activity_main);
    }
}