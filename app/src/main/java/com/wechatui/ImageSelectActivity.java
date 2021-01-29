package com.wechatui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ImageSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
        final int [] imgs = new int[]{R.drawable.img01,R.drawable.img02,R.drawable.img03,R.drawable.img04,R.drawable.img05,R.drawable.img06,R.drawable.img07,R.drawable.img08,R.drawable.img09};
        //SimpleAdapter adapter = new SimpleAdapter(this,);
        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return imgs.length;
            }

            @Override
            public Object getItem(int position) {
                return imgs[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, final View convertView, ViewGroup parent) {
                ImageView view = new ImageView(ImageSelectActivity.this);
                view.setImageResource(imgs[position]);
                view.setOnClickListener(v -> {
                    Intent intent = new Intent();
                    intent.putExtra("imageId",imgs[position]);
                    ImageSelectActivity.this.setResult(0,intent);
                    ImageSelectActivity.this.finish();
                });
                return view;
            }
        };
        GridView view = findViewById(R.id.gv);
        view.setAdapter(adapter);
    }
}