package com.hxh.componentdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hxh.annotations.BindPath;
import com.hxh.route.ARoute;

@BindPath("main/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("key", "from app module");
        ARoute.getInstance().jumpActivity(this, "member/member", bundle);
    }
}
