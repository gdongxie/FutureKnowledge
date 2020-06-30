package com.futures.knowledge.ui.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.futures.knowledge.BaseActivity;
import com.futures.knowledge.R;

public class FeedBackActivity extends BaseActivity {
    private ImageView iv_back;
    private EditText et_suggest;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        initView();
        initListener();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        et_suggest = findViewById(R.id.et_suggest);
        btn_submit = findViewById(R.id.btn_submit);
        loadingDialog.setHintText("正在提交...");
    }

    private void initListener() {
        et_suggest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    btn_submit.setBackground(getDrawable(R.drawable.bg_edit_normal));
                } else {
                    btn_submit.setBackground(getDrawable(R.drawable.bg_edit_unormal));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_suggest.getText().toString().trim())) {
                    loadingDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismiss();
                            Toast.makeText(FeedBackActivity.this, "我们已收到您的意见,谢谢",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }, 1000);
                } else {
                    Toast.makeText(FeedBackActivity.this, "请输入要反馈的意见或建议", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
