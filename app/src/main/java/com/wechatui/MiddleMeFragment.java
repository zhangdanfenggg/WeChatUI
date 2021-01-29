package com.wechatui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiddleMeFragment extends Fragment {

    Cursor cursor;
    DbOpenHelper helper;
    long id;
    //头像
    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //赋值
        helper = new DbOpenHelper(getActivity(),"wenchatui.db3",null,1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListView listView;
        //showname需设置监听器
        TextView username,showname,righttag;
        //列表项名称
        String[] names = new String[]{getString(R.string.wechatPay),getString(R.string.collect),
                getString(R.string.album),getString(R.string.cardPackage),
                getString(R.string.expression),getString(R.string.setting)};
        //右边大于号
        View view = inflater.inflate(R.layout.fragment_middle_me,container,false);
        listView = view.findViewById(R.id.listview_me);
        username = view.findViewById(R.id.username);
        showname = view.findViewById(R.id.showname);
        imageView = view.findViewById(R.id.headimage);
        righttag = view.findViewById(R.id.rightTag);


        List<Map<String,Object>> list = new ArrayList<>();
        for (String name : names) {
            Map<String, Object> map = new HashMap<>();
            map.put("header", name);
            map.put("name", getString(R.string.righttag));
            list.add(map);
        }
        //创建一个SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(),list,
                R.layout.simple_item,
                new String[]{"header","name"},
                new int[]{R.id.header,R.id.name});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Toast.makeText(getActivity(), "您点击了“" + listView.getItemIdAtPosition(position) + "”", Toast.LENGTH_SHORT).show();
            if (position==5)
            {
                Intent intent = new Intent();
                intent.setAction("setting");
                startActivity(intent);
            }
        });

        //设置微信号
        Intent intent = getActivity().getIntent();
        String phone = intent.getStringExtra("username");

        cursor = helper.getReadableDatabase().rawQuery("select * from users where phonenumber=?",new String[]{phone});
        cursor.moveToFirst();
        id = cursor.getInt(0);
        String name = cursor.getString(1);
        int image = cursor.getInt(4);
        showname.setText(name);
        username.setText("微信号："+phone);
        imageView.setImageResource(image);
        cursor.close();

        //设置监听器
        View.OnClickListener listener = v -> {
            Intent intent1 = new Intent();
            intent1.setAction("person_message");
            intent1.putExtra("id",id);
            intent1.putExtra("name",name);
            intent1.putExtra("wechat_number",phone);
            intent1.putExtra("image",image);
            startActivity(intent1);
        };
        showname.setOnClickListener(listener);
        righttag.setOnClickListener(listener);
        imageView.setOnClickListener(listener);
        return view;
    }

    @Override
    public void onResume() {
        Log.i("msg_id",String.valueOf(id));
        cursor = helper.getReadableDatabase().rawQuery("select * from users where _id=?",
                new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        imageView.setImageResource(cursor.getInt(4));
        cursor.close();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (helper!=null)
        {
            helper.close();
        }
    }
}