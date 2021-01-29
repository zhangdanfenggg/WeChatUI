package com.wechatui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LoginRegisterActivity extends AppCompatActivity {

    ActionBar actionBar;
    Button button_login,button_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        //赋值
        button_login = findViewById(R.id.wechat_login);
        button_register = findViewById(R.id.wechat_register);
        actionBar = getSupportActionBar();
        //操作
        actionBar.hide();
        //
        button_login.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("login");
            startActivityForResult(intent,0x111);
        });
        button_register.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("register");
            startActivity(intent);
        });
    }
}