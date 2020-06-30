package com.futures.knowledge.ui.activitys;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.andreabaccega.formedittextvalidator.Validator;
import com.andreabaccega.widget.FormEditText;
import com.futures.knowledge.BaseActivity;
import com.futures.knowledge.R;
import com.futures.knowledge.utils.AssetsDatabaseManager;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private FormEditText et_username, et_password, et_confirmPwd;
    private CardView card_register;
    private TextView tv_protocol, tv_policy;
    private String userName, password;
    private AssetsDatabaseManager databaseManager = AssetsDatabaseManager.getManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resister);
        initView();
        initListener();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        et_username = findViewById(R.id.et_userName);
        et_password = findViewById(R.id.et_passWord);
        et_confirmPwd = findViewById(R.id.et_confirm_pwd);
        card_register = findViewById(R.id.card_register);
        tv_protocol = findViewById(R.id.tv_protocol);
        tv_policy = findViewById(R.id.tv_policy);
        et_username.setFocusable(true);
        et_username.requestFocus();
        et_username.addValidator(new PhoneValidator());
        et_password.addValidator(new PassWordValidator());
    }

    private void initListener() {
        iv_back.setOnClickListener(this);
        card_register.setOnClickListener(this);
        tv_protocol.setOnClickListener(this);
        tv_policy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.card_register:
                register();
                break;
            case R.id.tv_protocol:
                toAgreement();
                break;
            case R.id.tv_policy:
                toAgreement();
                break;
            default:
                break;
        }
    }

    private void toAgreement() {
        startActivity(new Intent(RegisterActivity.this, AgreementActivity.class));
    }

    private void register() {
        if (et_username.testValidity() && et_password.testValidity()) {
            userName = et_username.getText().toString();
            password = et_password.getText().toString();
            if (!et_confirmPwd.getText().toString().equals(password)) {
                et_confirmPwd.setError("两次密码输入不一致");
            } else {
                loadingDialog.show();
                SQLiteDatabase sqLiteDatabase = databaseManager.getDatabase("qh.sqlite3");
                ContentValues contentValues = new ContentValues();
                contentValues.put("mobile", userName);
                contentValues.put("nickname", userName);
                contentValues.put("password", password);
                final long l = sqLiteDatabase.insert("user", null, contentValues);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismiss();
                        if (l > 0L) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册失败,该账号已存在！",
                                    Toast.LENGTH_SHORT).show();
                            et_username.requestFocus();
                            et_username.requestFocusFromTouch();
                        }
                    }
                }, 1000);
                databaseManager.closeDatabase("qh.sqlite3");
            }
        }

    }

    private static class PhoneValidator extends Validator {
        String telRegex = "^((13[0-9])|(14[5,6,7,9])|(15[^4])|(16[5,6])|(17[0-9])|(18[0-9])|" +
                "(19[1,8,9]))\\d{8}$";

        public PhoneValidator() {
            super("手机号不正确");
        }

        @Override
        public boolean isValid(EditText et) {
            return et.getText().toString().matches(telRegex);
        }
    }

    private static class PassWordValidator extends Validator {

        public PassWordValidator() {
            super("密码不能少于6位");
        }

        @Override
        public boolean isValid(EditText et) {
            return et.getText().toString().length() >= 6;
        }
    }
}
