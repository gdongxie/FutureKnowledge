package com.futures.knowledge.ui.activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.futures.knowledge.BaseActivity;
import com.futures.knowledge.R;
import com.futures.knowledge.bean.DataBean;
import com.futures.knowledge.utils.AssetsDatabaseManager;

public class InfoDetailsActivity extends BaseActivity {
    private ImageView iv_back, iv_icon;
    private TextView tv_title, tv_content;
    private AssetsDatabaseManager databaseManager = AssetsDatabaseManager.getManager();
    private DataBean dataBean;
    private int id;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_details);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        initView();
        initData();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        iv_icon = findViewById(R.id.iv_icon);
        tv_content = findViewById(R.id.tv_content);
        cardView = findViewById(R.id.card_icon);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void initData() {
        SQLiteDatabase sqLiteDatabase = databaseManager.getDatabase("qh.sqlite3");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select * from zixun where id='");
        stringBuilder.append(id);
        stringBuilder.append("'");
        Cursor cursor = sqLiteDatabase.rawQuery(String.valueOf(stringBuilder), null);

        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String desc = cursor.getString(cursor.getColumnIndex("desc"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String icon = cursor.getString(cursor.getColumnIndex("icon"));
            dataBean = new DataBean();
            dataBean.setId(id);
            dataBean.setTitle(title);
            dataBean.setDesc(desc);
            dataBean.setTime(time);
            dataBean.setIcon(icon);
        }
        if (dataBean != null) {
            tv_title.setText(dataBean.getTitle());
            tv_content.setText(dataBean.getDesc());
            if (dataBean.getIcon() != null) {
                iv_icon.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.VISIBLE);
                Glide.with(InfoDetailsActivity.this).load(dataBean.getIcon()).into(iv_icon);
            } else {
                iv_icon.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
            }
        }

    }

}
