package com.futures.knowledge.ui.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.futures.knowledge.R;
import com.futures.knowledge.bean.DataBean;
import com.futures.knowledge.ui.adapter.NewsAdapter;
import com.futures.knowledge.utils.AssetsDatabaseManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: OutlineFragment
 * @Description:
 * @Author: dongxie
 * @CreateDate: 2020/6/25 21:24
 */
public class OutlineFragment extends Fragment {
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<DataBean> dataBeanList = new ArrayList<>();
    private AssetsDatabaseManager databaseManager = AssetsDatabaseManager.getManager();
    private NewsAdapter dataAdapter;

    public static OutlineFragment newInstance() {
        OutlineFragment fragment = new OutlineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_outline, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        SQLiteDatabase sqLiteDatabase = databaseManager.getDatabase("qh.sqlite3");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select * from zixun where type='");
        stringBuilder.append("考试大纲");
        stringBuilder.append("'");
        Cursor cursor = sqLiteDatabase.rawQuery(String.valueOf(stringBuilder), null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String desc = cursor.getString(cursor.getColumnIndex("desc"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String icon = cursor.getString(cursor.getColumnIndex("icon"));
            DataBean dataBean = new DataBean();
            dataBean.setId(id);
            dataBean.setTitle(title);
            dataBean.setDesc(desc);
            dataBean.setTime(time);
            dataBean.setIcon(icon);
            dataBeanList.add(dataBean);
        }
    }

    private void initView(View root) {
        refreshLayout = root.findViewById(R.id.refreshLayout);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataAdapter = new NewsAdapter(getContext(), R.layout.item_data, dataBeanList);
        recyclerView.setAdapter(dataAdapter);
        refreshLayout.setDragRate(0.5f);
        refreshLayout.setReboundDuration(300);
        refreshLayout.setHeaderHeight(100);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshlayout.finishLoadMore(true);
                    }
                }, 1000);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshlayout.finishLoadMore(true);
                    }
                }, 500);
            }

        });


    }
}
