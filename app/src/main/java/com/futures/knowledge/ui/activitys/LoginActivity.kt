package com.futures.knowledge.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.andreabaccega.formedittextvalidator.Validator
import com.futures.knowledge.BaseActivity
import com.futures.knowledge.R
import com.futures.knowledge.utils.AssetsDatabaseManager
import com.futures.knowledge.utils.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    private val databaseManager = AssetsDatabaseManager.getManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        initListener()
    }

    private fun initView() {
        et_userName.addValidator(PhoneValidator())
        et_passWord.addValidator(PassWordValidator())
    }

    private fun initListener() {
        iv_back.setOnClickListener(this)
        tv_register.setOnClickListener(this)
        tv_policy.setOnClickListener(this)
        tv_protocol.setOnClickListener(this)
        card_login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> finish()
            R.id.tv_register -> startActivity(Intent(this, RegisterActivity::class.java))
            R.id.tv_policy -> startActivity(Intent(this, AgreementActivity::class.java))
            R.id.tv_protocol -> startActivity(Intent(this, AgreementActivity::class.java))
            R.id.card_login -> {
                if (et_userName.testValidity() && et_passWord.testValidity()) {
                    login()
                }
            }
        }
    }

    private fun login() {
        loadingDialog.show()
        val sqLiteDatabase = databaseManager.getDatabase("qh.sqlite3")
        var userName = et_userName.text.toString()
        var password = et_passWord.text.toString()
        var sb = StringBuilder()
        sb.append("select * from user where mobile='")
        sb.append(userName)
        sb.append("' and password='")
        sb.append(password)
        sb.append("'")
        var cursor = sqLiteDatabase.rawQuery(sb.toString(), null)
        Handler().postDelayed(Runnable {
            if (cursor.count > 0) {
                loadingDialog.dismiss()
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                var username = ""
                while (cursor != null && cursor.moveToNext()) {
                    username = cursor.getString(cursor.getColumnIndex("mobile"))
                }
                SharedPreferencesUtil.putString(applicationContext, "username", username)
                SharedPreferencesUtil.putBoolean(applicationContext, "isLogin", true)
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("index", 3)
                startActivity(intent)
            } else {
                loadingDialog.dismiss()
                Toast.makeText(this, "登录失败,用户名或密码错误", Toast.LENGTH_SHORT).show()
            }
        }, 1000)

    }

    private class PhoneValidator : Validator("手机号不正确") {
        var telRegex = "^((13[0-9])|(14[5,6,7,9])|(15[^4])|(16[5,6])|(17[0-9])|(18[0-9])|" +
                "(19[1,8,9]))\\d{8}$"

        override fun isValid(et: EditText): Boolean {
            return et.text.toString().matches(telRegex.toRegex())
        }
    }

    private class PassWordValidator : Validator("密码不能少于6位") {
        override fun isValid(et: EditText): Boolean {
            return et.text.toString().length >= 6
        }
    }
}