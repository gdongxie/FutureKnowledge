package com.futures.knowledge;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

/**
 * @ClassName: BaseActivity
 * @Description:
 * @Author: dongxie
 * @CreateDate: 2020/6/24 9:59
 */
public class BaseActivity extends AppCompatActivity {
    public ZLoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).statusBarColor(R.color.colorBlue).fitsSystemWindows(true).init();
        initLoading();
    }

    private void initLoading() {
        loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)
                .setLoadingColor(getColor(R.color.colorLoading))
                .setCancelable(false)
                .setHintTextSize(16f)
                .setHintTextColor(getColor(R.color.colorWhite))
                .setDialogBackgroundColor(getColor(R.color.colorLoadingBackground));
    }
}
