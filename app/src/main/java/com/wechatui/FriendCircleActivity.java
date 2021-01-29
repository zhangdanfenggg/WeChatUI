package com.wechatui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FriendCircleActivity extends AppCompatActivity {
    //用户id
    long id;
    //内容id
    ActionBar actionBar;
    ListView friend_circle_lv;
    DbOpenHelper helper;
    Cursor cursor;
    Cursor query;
    TextView friend_circle_name;
    ImageView friend_circle_image;
    //被点击的菜单项的位置
    int item_position;
    ArrayList<Integer> delete = new ArrayList<>();
    int min_delete = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_circle_new);
        //赋值
        actionBar = getSupportActionBar();
        friend_circle_lv = findViewById(R.id.friend_circle_lv);
        friend_circle_name = findViewById(R.id.friend_circle_name);
        friend_circle_image = findViewById(R.id.friend_circle_image);
        helper = new DbOpenHelper(this,"wenchatui.db3",null,1);
        Intent intent = getIntent();
        id = intent.getLongExtra("id",0L);

        //添加日志查看id的值
        Log.i("msg_find_circle",String.valueOf(id));
        //添加日志查看id的值
        Log.i("msg_find_circle",String.valueOf(item_position));

        actionBar.setDisplayHomeAsUpEnabled(true);
        friend_circle_lv.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            item_position = position;
            simple(view);
        });

           //显示昵称和头像
           query = helper.getReadableDatabase().rawQuery("select * from users where _id=?",new String[]{String.valueOf(id)});
           if (query.getCount()!=0)
           {
               query.moveToFirst();
               /*用于测试*/
//               Log.i("msg_i",i);
//               Log.i("msg_t",String.valueOf(i));
               if (friend_circle_image!=null&&friend_circle_name!=null)
               {
                   friend_circle_name.setText(query.getString(1));
                   friend_circle_image.setImageResource(query.getInt(4));
               }
           }
           query.close();
    }


    @Override
    protected void onResume() {

        cursor = helper.getReadableDatabase().rawQuery("select u.imageid,u.name,f.text,f.time,f._id " +
                        "from friend_circle f inner join users u " +
                        "where f.uid=u._id and f.isdelete=false and u._id = ?",new String[]{String.valueOf(id)});
        SimpleAdapter adapter = new SimpleAdapter(this,converCursorToList(cursor),
                R.layout.friend_circle_basic_item,new String[]{"imageid","name","text","time","delete"},
                new int[]{R.id.basic_item_image,R.id.basic_item_name,R.id.basic_item_text,
                R.id.basic_item_time,R.id.basic_item_delete});
        friend_circle_lv.setAdapter(adapter);
        super.onResume();
    }

    protected ArrayList<Map<String,Object>> converCursorToList(Cursor cursor)
    {
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Map<String,Object> map = new HashMap<>();
            int ID = cursor.getInt(0);
            String name = cursor.getString(1);
            friend_circle_name.setText(name);
            friend_circle_image.setImageResource(ID);
            map.put("imageid",ID);
            map.put("name",name);
            map.put("text",cursor.getString(2));
            long date_time = cursor.getLong(3);
            Date date = new Date();
            long time = (date.getTime()-date_time)/1000;//秒
            String result;
            if (time>=0&&time<60)
            {
                result = "刚刚";
            }
            else if (time<60*60)
            {
                result = time / 60 +"分钟前";
            }
            else if (time<60*60*24)
            {
                result = time / (60 * 60) +"小时前";
            }
            else {
                result = time / (60 * 60 * 24) +"天前";
            }
            map.put("time",result);
            map.put("delete","删除");
            list.add(map);
        }
        return list;
    }

    private int circle_delete(int id){
        if (!delete.isEmpty()&id >= min_delete) {
            for (Integer integer : delete) {
                if (id >= integer) {
                    id++;
                }
            }
        }
        return id;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_item:
                Intent intent = new Intent();
                intent.setAction("WriteText");
                intent.putExtra("id",id);
                startActivity(intent);
                break;
        }
        return true;
    }

    public void simple(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("删除该朋友圈？");
                setPositionButton(builder);
                setNegativeButton(builder);
                builder.create();
                builder.show();
    }

    private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder) {
        return builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());
    }

    private AlertDialog.Builder setPositionButton(AlertDialog.Builder builder) {
        return builder.setPositiveButton("确定", (dialog, which) -> {
                if (item_position<min_delete)
                    min_delete = item_position;
                delete.add(item_position);
                Log.i("msg_value",String.valueOf(item_position));
                helper.getWritableDatabase().execSQL("update friend_circle " +
                        "set isdelete='true' where _id=?",new Object[]{circle_delete(item_position)});
                this.onResume();
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