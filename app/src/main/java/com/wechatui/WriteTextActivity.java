package com.wechatui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class WriteTextActivity extends AppCompatActivity {

    ActionBar actionBar;
    //编辑的文字
    EditText write_text_text;
    DbOpenHelper helper;
    //用户id
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_text);
        //赋值
        actionBar = getSupportActionBar();
        write_text_text = findViewById(R.id.write_text_text);
        helper = new DbOpenHelper(this,"wenchatui.db3",null,1);
        Intent intent = getIntent();
        id = intent.getLongExtra("id",0L);

        //添加日志查看id的值
        Log.i("msg_write_text",String.valueOf(id));

        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.button_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.button_menu:
                helper.getWritableDatabase().execSQL("insert into friend_circle values(?,?,?,?,?)",
                        new Object[]{null, id, write_text_text.getText().toString(),(new Date()).getTime(),false});
                WriteTextActivity.this.finish();
                break;
        }
        return true;
    }
    @Override
    protected void onDestroy() {
        if (helper!=null)
        {
            helper.close();
        }
        super.onDestroy();
    }
}