package com.futures.knowledge.ui.activitys;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.futures.knowledge.BaseActivity;
import com.futures.knowledge.R;
import com.futures.knowledge.bean.ExamListBean;
import com.futures.knowledge.ui.adapter.ExamListAdapter;
import com.futures.knowledge.utils.AssetsDatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class ExamListActivity extends BaseActivity {
    private ImageView iv_back;
    private TextView tv_title;
    private RecyclerView recyclerView;
    private AssetsDatabaseManager databaseManager = AssetsDatabaseManager.getManager();
    private String title;
    private List<ExamListBean> examListBeans = new ArrayList<>();
    private ExamListAdapter examListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);
        title = getIntent().getStringExtra("title");
        initView();
        initData();
    }


    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText(title);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExamListActivity.this));
        examListAdapter = new ExamListAdapter(ExamListActivity.this,
                R.layout.item_list_exam, examListBeans);
        recyclerView.setAdapter(examListAdapter);
    }


    private void initData() {
        SQLiteDatabase sqLiteDatabase = databaseManager.getDatabase("qh.sqlite3");
        String sql = "";
        if ("章节练习".equals(title)) {
            sql = "select * from chapter";
        } else if ("历年真题".equals(title)) {
            sql = "select * from exam where type='HISTORY'";
        } else if ("模拟考试".equals(title)||"巩固模拟".equals(title)) {
            sql = "select * from exam where type='SIMULATION'";
        }
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            ExamListBean listBean = new ExamListBean();
            listBean.setTitle(title);
            listBean.setId(id);
            listBean.setNum("");
            examListBeans.add(listBean);
        }
        for (int i = 0; i < examListBeans.size(); i++) {
            String sqlNum =
                    "select count(id) from problem where exam_id='" + examListBeans.get(i).getId() + "'";
            Cursor cursorNum = sqLiteDatabase.rawQuery(sqlNum, null);
            if (cursorNum.moveToFirst()) {
                int num = cursorNum.getInt(0);
                examListBeans.get(i).setNum(String.valueOf(num));
            }
        }
        examListAdapter.notifyDataSetChanged();
        databaseManager.closeDatabase("qh.sqlite3");
    }
}
