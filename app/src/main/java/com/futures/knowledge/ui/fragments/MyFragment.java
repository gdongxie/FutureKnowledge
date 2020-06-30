package com.futures.knowledge.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.futures.knowledge.R;
import com.futures.knowledge.ui.activitys.AgreementActivity;
import com.futures.knowledge.ui.activitys.FeedBackActivity;
import com.futures.knowledge.ui.activitys.LoginActivity;
import com.futures.knowledge.utils.AssetsDatabaseManager;
import com.futures.knowledge.utils.SharedPreferencesUtil;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.Objects;


public class MyFragment extends Fragment {
    private CardView cardLogin;
    private TextView tv_username, tv_login;
    private RelativeLayout rl_suggest, rl_update, rl_about, rl_logOut;
    private TextView tv_policy, tv_protocol;
    private Intent intent;
    private ZLoadingDialog loadingDialog;
    private AssetsDatabaseManager databaseManager = AssetsDatabaseManager.getManager();

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        cardLogin = root.findViewById(R.id.card_login);
        tv_username = root.findViewById(R.id.tv_username);
        rl_suggest = root.findViewById(R.id.rl_suggest);
        tv_policy = root.findViewById(R.id.tv_policy);
        tv_protocol = root.findViewById(R.id.tv_protocol);
        rl_update = root.findViewById(R.id.rl_update);
        rl_about = root.findViewById(R.id.rl_about);
        rl_logOut = root.findViewById(R.id.rl_logOut);
        tv_login = root.findViewById(R.id.tv_login);
        loadingDialog = new ZLoadingDialog(getActivity());
        loadingDialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)
                .setLoadingColor(getActivity().getColor(R.color.colorLoading))
                .setCancelable(false)
                .setHintTextSize(16f)
                .setHintTextColor(getActivity().getColor(R.color.colorWhite))
                .setDialogBackgroundColor(getActivity().getColor(R.color.colorLoadingBackground));
        if (SharedPreferencesUtil.getBoolean(Objects.requireNonNull(getActivity()).getApplicationContext(), "isLogin", false)) {
            tv_username.setText(SharedPreferencesUtil.getString(getActivity().getApplicationContext(), "username", ""));
            tv_login.setText("退出");
            rl_logOut.setVisibility(View.VISIBLE);
        } else {
            tv_username.setText("");
            tv_login.setText("登录/注册");
            rl_logOut.setVisibility(View.INVISIBLE);
        }
        cardLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!SharedPreferencesUtil.getBoolean(Objects.requireNonNull(getActivity()).getApplicationContext(), "isLogin", false)) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    loadingDialog.setHintText("退出中...");
                    loadingDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismiss();
                            Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
                            SharedPreferencesUtil.putBoolean(getActivity().getApplicationContext(),
                                    "isLogin", false);
                            tv_login.setText("登录/注册");
                            tv_username.setText("");
                            rl_logOut.setVisibility(View.INVISIBLE);
                        }
                    }, 1000);

                }

            }
        });
        rl_suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
            }
        });
        tv_protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAgreement();
            }
        });
        tv_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAgreement();
            }
        });
        rl_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismiss();
                        Toast.makeText(getActivity(), "当前已是最新版本", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });

        rl_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("关于我们");
                builder.setMessage("期货题库宝\n专业全面的期货答题软件\n当前版本：V1.0.0");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        rl_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示");
                builder.setCancelable(false);
                builder.setMessage("注销操作不可逆，是否确定注销当前账号？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase sqLiteDatabase = databaseManager.getDatabase("qh.sqlite3");
                        String[] args =
                                {SharedPreferencesUtil.getString(getActivity().getApplicationContext(),
                                        "username", "")};
                        final int i = sqLiteDatabase.delete("user", "mobile=?", args);
                        loadingDialog.setHintText("注销中...");
                        loadingDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (i > 0) {
                                    loadingDialog.dismiss();
                                    Toast.makeText(getActivity().getApplicationContext(), "注销成功",
                                            Toast.LENGTH_SHORT).show();
                                    SharedPreferencesUtil.putBoolean(getActivity().getApplicationContext(),
                                            "isLogin", false);
                                    tv_login.setText("登录/注册");
                                    tv_username.setText("");
                                    rl_logOut.setVisibility(View.INVISIBLE);
                                } else {
                                    loadingDialog.dismiss();
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "注销失败，请稍后重试",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 1000);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });

    }

    private void toAgreement() {
        intent = new Intent(getActivity(), AgreementActivity.class);
        startActivity(intent);
    }
}
