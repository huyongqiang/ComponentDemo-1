package com.hxh.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hxh.annotations.BindPath;

@BindPath("login/login2")
public class LoginActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
