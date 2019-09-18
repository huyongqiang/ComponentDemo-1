package com.hxh.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hxh.annotations.BindPath;

@BindPath("login/login")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
