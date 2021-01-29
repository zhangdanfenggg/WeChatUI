package com.wechatui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiddleFindFragment extends Fragment {
    //获取用户id
    long _id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        _id = intent.getLongExtra("id", 0L);

        //添加日志查看id的值
        Log.i("msg_find_fragment",String.valueOf(_id));

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_middle_find, container, false);
        ListView friend_circle_listview = view.findViewById(R.id.friend_circle_listview);
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("header",getString(R.string.friendcircle));
        map.put("desc",getString(R.string.righttag));
        list.add(map);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(),list,R.layout.simple_item,
                new String[]{"header","desc"},new int[]{R.id.header,R.id.name});
        friend_circle_listview.setAdapter(adapter);

        friend_circle_listview.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent();
            intent.setAction("FriendCircle");
            intent.putExtra("id",_id);
            startActivity(intent);
        });
        return view;
    }
}