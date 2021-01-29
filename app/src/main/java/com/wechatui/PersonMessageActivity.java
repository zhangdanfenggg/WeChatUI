package com.wechatui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonMessageActivity extends AppCompatActivity {

    ListView listview_person;
    TextView header,head_name,name;
    ImageView person_image_setting;
    ActionBar actionBar;
    DbOpenHelper helper;
    Cursor cursor;
    //修改过的图片的id
    int t;
    //用户id
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_message);
        //赋值
        View view = getLayoutInflater().inflate(R.layout.simple_item,null);
        listview_person = findViewById(R.id.listview_person);
        header = view.findViewById(R.id.header);
        head_name = view.findViewById(R.id.head_name);
        name = view.findViewById(R.id.name);
        person_image_setting = view.findViewById(R.id.person_image_setting);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        helper = new DbOpenHelper(this,"wenchatui.db3",null,1);
        Intent intent = getIntent();
        id = intent.getLongExtra("id",0L);//不知为何会为0
        Log.i("msg_person",String.valueOf(id));
        //为ListView添加监听事件
        listview_person.setOnItemClickListener((parent, view1, position, id) -> {
            switch (position)
            {
                case 0:
                    Intent intent1 = new Intent();
                    intent1.setAction("imageselect");
                    startActivityForResult(intent1,0);
                    break;
                case 1:
                case 3:
                    break;
                case 2:
                    Toast.makeText(PersonMessageActivity.this,"你的微信号是"+
                            intent.getStringExtra("wechat_number"),Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    @Override
    protected void onResume() {
        cursor = helper.getReadableDatabase().rawQuery("select * from users where _id=?",new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        String name = cursor.getString(1);
        String phonenumber = cursor.getString(2);
        int imageid = cursor.getInt(4);
        cursor.close();
        //列表项名称
        int[] names = new int[]{R.string.headimage, R.string.name, R.string.wechat_number, R.string.sex};
        List<Map<String,Object>> list = new ArrayList<>();
        for (int i=0;i<names.length;i++)
        {
            Map<String,Object> map = new HashMap<>();
            map.put("header",getString(names[i]));
            map.put("name",getString(R.string.righttag));
            switch (i)
            {
                case 0:
                    map.put("person",imageid);
                    map.put("headname","");
                    break;
                case 1:
                    map.put("person",0);
                    map.put("headname",name);
                    break;
                case 2:
                    map.put("person",0);
                    map.put("headname",phonenumber);
                    break;
                case 3:
                    map.put("person",0);
                    map.put("headname","男");
                    break;
            }
            list.add(map);
        }
        //创建一个SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,
                R.layout.simple_item,
                new String[]{"header","name","person","headname"},
                new int[]{R.id.header,R.id.name,R.id.person_image_setting,R.id.head_name});
        listview_person.setAdapter(simpleAdapter);
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0  || resultCode == 0)
        {
            t = data != null ? data.getIntExtra("imageId", R.drawable.icon) : R.drawable.icon;
            helper.getWritableDatabase().execSQL("update users set imageid=? where _id=?",new Object[]{t,id});
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