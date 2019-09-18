package com.hxh.member;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hxh.annotations.BindPath;
import com.hxh.route.ARoute;

@BindPath("member/member")
public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        String extra = getIntent().getBundleExtra("bundle").getString("key");
        Toast.makeText(this, "参数传递:" + extra, Toast.LENGTH_SHORT).show();
    }

    public void click(View view) {
        ARoute.getInstance().jumpActivity("login/login", null);
    }
}
