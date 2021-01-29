package com.wechatui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    ActionBar actionBar;
    ImageView register_image;
    DbOpenHelper helper;
    EditText register_name,register_username,register_password;
    CheckBox radiobutton;
    Button register;
    TextView register_chahao;
    //选中的图片id
    int t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //赋值
        actionBar = getSupportActionBar();
        register_image = findViewById(R.id.register_image);
        register = findViewById(R.id.register);
        register_name = findViewById(R.id.register_name);
        register_username = findViewById(R.id.register_username);
        register_password = findViewById(R.id.register_password);
        radiobutton = findViewById(R.id.radiobutton);
        register_chahao = findViewById(R.id.register_chahao);
        helper = new DbOpenHelper(this,"wenchatui.db3",null,1);

        if (actionBar != null) {
            actionBar.hide();
        }
        register_image.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("imageselect");
            startActivityForResult(intent,0);
        });
        //为左上角添加监听器
        register_chahao.setOnClickListener(v -> finish());
        //为单选按钮添加监听器
        radiobutton.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isChecked)
            {
                register.setEnabled(true);
            }
            else
            {
                register.setEnabled(false);
            }
        });
        //为注册按钮添加监听器
        register.setOnClickListener(v -> {
            String name,phone,password;
            name = register_name.getText().toString().length() == 0?getString(R.string.wechat):register_name.getText().toString();
            phone = register_username.getText().toString();
            password = register_password.getText().toString();
            if (phone.length()!=0 && password.length()!=0)
            {
                try {
                    helper.getWritableDatabase().execSQL("insert into users values(?,?,?,?,?)"
                            ,new Object[]{null, name, phone, password, t});
                    Toast.makeText(this,"注册成功",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setAction("login");
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(this,"注册失败",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0  || resultCode == 0)
        {
            t = data != null ? data.getIntExtra("imageId", R.drawable.icon) : R.drawable.icon;
            register_image.setImageResource(t);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helper!=null)
        {
            helper.close();
        }
    }
}