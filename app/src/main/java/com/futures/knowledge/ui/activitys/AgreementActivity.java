package com.futures.knowledge.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.futures.knowledge.BaseActivity;
import com.futures.knowledge.R;

public class AgreementActivity extends BaseActivity {
    private WebView webView;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        webView = findViewById(R.id.webView);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView.loadUrl("file:////android_asset/html/anp.html");
    }
}
