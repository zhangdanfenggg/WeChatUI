package com.wechatui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    ActionBar actionBar;
    TextView chahao;
    EditText writephone,login_password;
    Cursor cursor;
    Button login;
    DbOpenHelper helper;
    //用户id
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //赋值
        actionBar = getSupportActionBar();
        chahao = findViewById(R.id.chahao);
        writephone = findViewById(R.id.writephone);
        login = findViewById(R.id.login);
        login_password = findViewById(R.id.login_password);
        helper = new DbOpenHelper(this,"wenchatui.db3",null,1);

        actionBar.hide();
        //设置叉号
        chahao.setOnClickListener(v -> finish());
        //设置“下一步”按钮的可用状态
        login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                login.setEnabled(writephone.getText().toString().length() != 0 && login_password.getText().toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //为按钮"登录"添加监听器
        login.setOnClickListener(v -> {
            String username = writephone.getText().toString();
            String password = login_password.getText().toString();
            try {
                cursor=helper.getReadableDatabase().rawQuery("select password,_id from users where phonenumber=?",new String[]{username});
                cursor.moveToFirst();
                String pwd=cursor.getString(0);
                id = cursor.getInt(1);

                //添加日志查看id的值
                Log.i("msg_loginactivity",String.valueOf(id));

                if (pwd.equals(password))
                {
                    Intent intent = new Intent();
                    intent.setAction("main_page");
                    intent.putExtra("id",id);
                    intent.putExtra("username",username);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"用户名或密码错误!",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                Toast.makeText(this,"请先注册",Toast.LENGTH_LONG).show();
            }
        });
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