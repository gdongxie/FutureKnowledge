package com.futures.knowledge.ui.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.futures.knowledge.BaseActivity;
import com.futures.knowledge.R;
import com.futures.knowledge.bean.OptionBean;
import com.futures.knowledge.bean.ProblemBean;
import com.futures.knowledge.utils.AssetsDatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_title, tv_index, tv_question, tv_parse, tv_pre, tv_next, tv_type;
    private RadioGroup rg_answer;
    private RadioButton rb_a, rb_b, rb_c, rb_d;
    private String title, num;
    private AssetsDatabaseManager databaseManager = AssetsDatabaseManager.getManager();
    private int id;
    private List<ProblemBean> problemBeans = new ArrayList<>();
    private List<OptionBean> optionBeans = new ArrayList<>();
    private int currentProblemIndex = 0;
    private List<OptionBean> myOptionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        title = getIntent().getStringExtra("title");
        num = getIntent().getStringExtra("num");
        id = getIntent().getIntExtra("id", 0);
        initView();
        initData();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        tv_index = findViewById(R.id.tv_index);
        tv_question = findViewById(R.id.tv_question);
        tv_parse = findViewById(R.id.tv_parse);
        tv_pre = findViewById(R.id.tv_pre);
        tv_next = findViewById(R.id.tv_next);
        tv_type = findViewById(R.id.tv_type);
        rg_answer = findViewById(R.id.rg_answer);
        rb_a = findViewById(R.id.rb_a);
        rb_b = findViewById(R.id.rb_b);
        rb_c = findViewById(R.id.rb_c);
        rb_d = findViewById(R.id.rb_d);
        tv_title.setText(title);
        setListener();
    }


    private void initData() {
        SQLiteDatabase sqLiteDatabase = databaseManager.getDatabase("qh.sqlite3");
        String sql = "select * from problem where exam_id='" + id + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            ProblemBean problemBean = new ProblemBean();
            problemBean.setId(cursor.getInt(cursor.getColumnIndex("id")));
            problemBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            problemBean.setIndex(cursor.getInt(cursor.getColumnIndex("index")));
            problemBean.setType(cursor.getString(cursor.getColumnIndex("type")));
            problemBean.setRight_answer(cursor.getString(cursor.getColumnIndex("right_answer")));
            problemBean.setExplain(cursor.getString(cursor.getColumnIndex("explain")));
            problemBean.setMy_answer(cursor.getString(cursor.getColumnIndex("my_answer")));
            problemBeans.add(problemBean);
        }
        databaseManager.closeDatabase("qh.sqlite3");
        setData();
    }

    private void initAnswer(SQLiteDatabase sqLiteDatabase, int id) {
        optionBeans.clear();
        String optionSql = "select * from option where problem_id='" + id + "'";
        Cursor optionCursor = sqLiteDatabase.rawQuery(optionSql, null);
        while (optionCursor.moveToNext()) {
            OptionBean optionBean = new OptionBean();
            optionBean.setId(optionCursor.getInt(optionCursor.getColumnIndex("id")));
            optionBean.setValue(optionCursor.getString(optionCursor.getColumnIndex("value")));
            optionBean.setAnswer(optionCursor.getString(optionCursor.getColumnIndex("answer")));
            optionBeans.add(optionBean);
        }
        switch (optionBeans.size()) {
            case 2:
                rb_a.setText(String.format("%s　%s", optionBeans.get(0).getValue(),
                        optionBeans.get(0).getAnswer()));
                rb_b.setText(String.format("%s　%s", optionBeans.get(1).getValue(),
                        optionBeans.get(1).getAnswer()));
                rb_c.setVisibility(View.INVISIBLE);
                rb_d.setVisibility(View.INVISIBLE);
                break;
            case 4:
                rb_a.setText(String.format("%s　%s", optionBeans.get(0).getValue(),
                        optionBeans.get(0).getAnswer()));
                rb_b.setText(String.format("%s　%s", optionBeans.get(1).getValue(),
                        optionBeans.get(1).getAnswer()));
                rb_c.setText(String.format("%s　%s", optionBeans.get(2).getValue(),
                        optionBeans.get(2).getAnswer()));
                rb_d.setText(String.format("%s　%s", optionBeans.get(3).getValue(),
                        optionBeans.get(3).getAnswer()));
                break;
            default:
                break;
        }
    }


    private void setListener() {
        iv_back.setOnClickListener(this);
        tv_parse.setOnClickListener(this);
        tv_pre.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        rg_answer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_a:
                        myOptionList.add(optionBeans.get(0));
                        break;
                    case R.id.rb_b:
                        myOptionList.add(optionBeans.get(1));
                        break;
                    case R.id.rb_c:
                        myOptionList.add(optionBeans.get(2));
                        break;
                    case R.id.rb_d:
                        myOptionList.add(optionBeans.get(3));
                        break;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                if (currentProblemIndex != 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("当前已在答题，是否退出？");
                    builder.setCancelable(false);
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.show();
                } else {
                    finish();
                }
                break;
            case R.id.tv_pre:
                if (currentProblemIndex == 0) {
                    Toast.makeText(this, "当前已是第一题", Toast.LENGTH_SHORT).show();
                } else {
                    rb_a.setVisibility(View.VISIBLE);
                    rb_b.setVisibility(View.VISIBLE);
                    rb_c.setVisibility(View.VISIBLE);
                    rb_d.setVisibility(View.VISIBLE);
                    rb_a.setChecked(false);
                    rb_b.setChecked(false);
                    rb_c.setChecked(false);
                    rb_d.setChecked(false);
                    currentProblemIndex = currentProblemIndex - 1;
                    setData();
                }
                break;
            case R.id.tv_next:
                if (currentProblemIndex == problemBeans.size() - 1) {
                    Toast.makeText(this, "恭喜您，完成本次答题！", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    rb_a.setVisibility(View.VISIBLE);
                    rb_b.setVisibility(View.VISIBLE);
                    rb_c.setVisibility(View.VISIBLE);
                    rb_d.setVisibility(View.VISIBLE);
                    rb_a.setChecked(false);
                    rb_b.setChecked(false);
                    rb_c.setChecked(false);
                    rb_d.setChecked(false);
                    currentProblemIndex = currentProblemIndex + 1;
                    setData();
                }
                break;
            case R.id.tv_parse:
                AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
                builder.setTitle("答案解析");
                builder.setMessage(problemBeans.get(currentProblemIndex).getExplain());
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            default:
                break;
        }
    }

    private void setData() {
        SQLiteDatabase sqLiteDatabase = databaseManager.getDatabase("qh.sqlite3");
        tv_question.setText(problemBeans.get(currentProblemIndex).getIndex() + "." + problemBeans.get(currentProblemIndex).getTitle());
        tv_index.setText(problemBeans.get(currentProblemIndex).getIndex() + "/" + num);
        tv_type.setText(problemBeans.get(currentProblemIndex).getType());
        int answerId = problemBeans.get(currentProblemIndex).getId();
        String optionSql = "select * from option where problem_id='" + answerId + "'";
        Cursor optionCursor = sqLiteDatabase.rawQuery(optionSql, null);
        while (optionCursor.moveToNext()) {
            OptionBean optionBean = new OptionBean();
            optionBean.setId(optionCursor.getInt(optionCursor.getColumnIndex("id")));
            optionBean.setValue(optionCursor.getString(optionCursor.getColumnIndex("value")));
            optionBean.setAnswer(optionCursor.getString(optionCursor.getColumnIndex("answer")));
        }
        initAnswer(sqLiteDatabase, answerId);
    }


}
