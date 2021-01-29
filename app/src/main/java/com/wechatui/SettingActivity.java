package com.wechatui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {

    ListView listView_setting;
    ActionBar actionBar;
    final int[] names = new int[]{R.string.UsernameAndSafe,R.string.YouthMode,R.string.NewMessage,
        R.string.QuiteMode,R.string.Chat,R.string.Privacy,R.string.Common,R.string.AboutWeChat,
    R.string.Help,R.string.Plugin,R.string.Quit};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //赋值
        listView_setting = findViewById(R.id.listview_setting);
        actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        //
        List<Map<String,Object>> list = new ArrayList<>();
        for (int i=0;i<names.length;i++)
        {
            Map<String,Object> map = new HashMap<>();
            map.put("header",getString(names[i]));
            if (i!=1)
            {
                map.put("desc",getString(R.string.righttag));
            }
            else
            {
                map.put("desc",getString(R.string.Unopen));
            }
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,list,R.layout.simple_item,
                new String[]{"header","desc"},new int[]{R.id.header,R.id.name});
        listView_setting.setAdapter(adapter);
        //添加监听事件
        listView_setting.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 10) {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(SettingActivity.this,LoginRegisterActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(SettingActivity.this, "您点击了“"+
                        listView_setting.getItemIdAtPosition(position)+"”", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}