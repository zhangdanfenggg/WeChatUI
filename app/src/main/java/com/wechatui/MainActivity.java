package com.wechatui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    TextView buttom_wechat,buttom_phone,buttom_find,buttom_me;
    TextView tv_wechat,tv_search,tv_add;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //赋值
        //fragment_down里的四个导航文本
        buttom_wechat = findViewById(R.id.bottom_wechat);
        buttom_phone = findViewById(R.id.bottom_phone);
        buttom_find = findViewById(R.id.bottom_find);
        buttom_me = findViewById(R.id.bottom_me);
        //fragment_up布局中的三个文本
        tv_wechat = findViewById(R.id.tv_wechat);
        tv_search = findViewById(R.id.tv_search);
        tv_add = findViewById(R.id.tv_add);
        layout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.fragment_up,null);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.framelayout,new MiddleWeChatFragment())
                .commit();

        //关闭ActionBar
        getSupportActionBar().hide();
        //添加监听器,并设置四个界面
        View.OnClickListener listener = v -> {
            int id = v.getId();
            switch (id)
            {
                case R.id.bottom_phone:
                    MiddlePhoneFragment middlePhoneFragment = new MiddlePhoneFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout,middlePhoneFragment)
                            .commit();
                    buttom_phone.setTextColor(Color.GREEN);
                    buttom_wechat.setTextColor(Color.GRAY);
                    buttom_find.setTextColor(Color.GRAY);
                    buttom_me.setTextColor(Color.GRAY);
                    //设置页面上边三个组件的可见性及显示的文字
                    tv_wechat.setText(R.string.phone);
                    tv_add.setVisibility(View.VISIBLE);
                    tv_search.setVisibility(View.VISIBLE);
                    tv_wechat.setVisibility(View.VISIBLE);
                    break;
                case R.id.bottom_find:
                    MiddleFindFragment middleFindFragment = new MiddleFindFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout,middleFindFragment)
                            .commit();
                    buttom_phone.setTextColor(Color.GRAY);
                    buttom_wechat.setTextColor(Color.GRAY);
                    buttom_find.setTextColor(Color.GREEN);
                    buttom_me.setTextColor(Color.GRAY);
                    //设置页面上边三个组件的可见性及显示的文字
                    tv_wechat.setText(R.string.find);
                    tv_add.setVisibility(View.VISIBLE);
                    tv_search.setVisibility(View.VISIBLE);
                    tv_wechat.setVisibility(View.VISIBLE);
                    break;
                case R.id.bottom_me:
                    MiddleMeFragment middleMeFragment = new MiddleMeFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout,middleMeFragment)
                            .commit();
                    buttom_phone.setTextColor(Color.GRAY);
                    buttom_wechat.setTextColor(Color.GRAY);
                    buttom_find.setTextColor(Color.GRAY);
                    buttom_me.setTextColor(Color.GREEN);
                    //设置页面上边三个组件的可见性
                    tv_add.setVisibility(View.GONE);
                    tv_search.setVisibility(View.GONE);
                    tv_wechat.setVisibility(View.GONE);
                    layout.setBackgroundColor(Color.WHITE);

                    break;
                default:
                    MiddleWeChatFragment middleWeChatFragment = new MiddleWeChatFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.framelayout,middleWeChatFragment)
                            .commit();
                    buttom_phone.setTextColor(Color.GRAY);
                    buttom_wechat.setTextColor(Color.GREEN);
                    buttom_find.setTextColor(Color.GRAY);
                    buttom_me.setTextColor(Color.GRAY);
                    //设置页面上边三个组件的可见性及显示的文字
                    tv_wechat.setText(R.string.wechat);
                    tv_add.setVisibility(View.VISIBLE);
                    tv_search.setVisibility(View.VISIBLE);
                    tv_wechat.setVisibility(View.VISIBLE);
                    break;
            }
        };
        //为底部导航添加监听器
        buttom_wechat.setOnClickListener(listener);
        buttom_phone.setOnClickListener(listener);
        buttom_find.setOnClickListener(listener);
        buttom_me.setOnClickListener(listener);
        //为加号添加监听器
        View root = this.getLayoutInflater().inflate(R.layout.popup,null);
        final PopupWindow window = new PopupWindow(root,300,150);
        tv_add.setOnClickListener(v -> {
            if (window.isShowing())
            {
                window.dismiss();
            }
            else
            {
                window.showAsDropDown(v);
            }

        });
    }
}