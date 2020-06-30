package com.futures.knowledge.ui.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.futures.knowledge.R;
import com.futures.knowledge.bean.BannerBean;
import com.futures.knowledge.ui.activitys.ExamActivity;
import com.futures.knowledge.ui.activitys.ExamListActivity;
import com.futures.knowledge.ui.activitys.LoginActivity;
import com.futures.knowledge.ui.activitys.WebViewActivity;
import com.futures.knowledge.ui.adapter.BannerAdapter;
import com.futures.knowledge.utils.SharedPreferencesUtil;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout card_practice, card_pastExam, card_simulateExam, rl_strength,
            rl_random, rl_everyday;
    private CardView card_qualification;
    private Banner banner;
    private List<BannerBean> bannerBeans = new ArrayList<>();
    private TextView textView;
    private Typeface textTypeface;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(root);
        return root;
    }

    private void initViews(View root) {
        card_qualification = root.findViewById(R.id.card_qualification);
        card_practice = root.findViewById(R.id.rl_practice);
        card_pastExam = root.findViewById(R.id.rl_past_exam);
        card_simulateExam = root.findViewById(R.id.rl_simulate_exam);
        rl_strength = root.findViewById(R.id.rl_strength);
        rl_random = root.findViewById(R.id.rl_random);
        rl_everyday = root.findViewById(R.id.rl_everyday);
        banner = root.findViewById(R.id.banner);
        textView = root.findViewById(R.id.tv_bottom);
        textTypeface = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets()
                , "fonts" +
                "/FZLanTingHeiS-L-GB-Regular.TTF");
        textView.setTypeface(textTypeface);
        initBanner();
        initListener();

    }

    private void initBanner() {
        BannerBean bannerBean1 = new BannerBean();
        bannerBean1.setImageRes(R.drawable.bg1);
        BannerBean bannerBean2 = new BannerBean();
        bannerBean2.setImageRes(R.drawable.bg2);
        BannerBean bannerBean3 = new BannerBean();
        bannerBean3.setImageRes(R.drawable.bg8);
        bannerBeans.add(bannerBean1);
        bannerBeans.add(bannerBean2);
        bannerBeans.add(bannerBean3);
        banner.addBannerLifecycleObserver(this)
                .setAdapter(new BannerAdapter(bannerBeans))
                .setIndicator(new CircleIndicator(getActivity().getApplicationContext()))
                .start();
    }

    private void initListener() {
        card_qualification.setOnClickListener(this);
        card_practice.setOnClickListener(this);
        card_pastExam.setOnClickListener(this);
        card_simulateExam.setOnClickListener(this);
        rl_strength.setOnClickListener(this);
        rl_random.setOnClickListener(this);
        rl_everyday.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_qualification:
                startActivity(new Intent(getContext(), WebViewActivity.class));
                break;
            case R.id.rl_practice:
                if (SharedPreferencesUtil.getBoolean(Objects.requireNonNull(getActivity()).getApplicationContext(),
                        "isLogin", false)) {
                    Intent intent = new Intent(getContext(), ExamListActivity.class);
                    intent.putExtra("title", "章节练习");
                    getActivity().startActivity(intent);
                } else {
                    getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.rl_past_exam:
                if (SharedPreferencesUtil.getBoolean(Objects.requireNonNull(getActivity()).getApplicationContext(),
                        "isLogin", false)) {
                    Intent intent = new Intent(getContext(), ExamListActivity.class);
                    intent.putExtra("title", "历年真题");
                    getActivity().startActivity(intent);
                } else {
                    getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.rl_simulate_exam:
                if (SharedPreferencesUtil.getBoolean(Objects.requireNonNull(getActivity()).getApplicationContext(),
                        "isLogin", false)) {
                    Intent intent = new Intent(getContext(), ExamListActivity.class);
                    intent.putExtra("title", "模拟考试");
                    getActivity().startActivity(intent);
                } else {
                    getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.rl_strength:
                if (SharedPreferencesUtil.getBoolean(Objects.requireNonNull(getActivity()).getApplicationContext(),
                        "isLogin", false)) {
                    Intent intent = new Intent(getContext(), ExamListActivity.class);
                    intent.putExtra("title", "巩固模拟");
                    getActivity().startActivity(intent);
                } else {
                    getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.rl_random:
                if (SharedPreferencesUtil.getBoolean(Objects.requireNonNull(getActivity()).getApplicationContext(),
                        "isLogin", false)) {
                    Intent intent = new Intent(getContext(), ExamActivity.class);
                    intent.putExtra("title", "随机检验");
                    intent.putExtra("id", 4);
                    intent.putExtra("num", "29");
                    getActivity().startActivity(intent);
                } else {
                    getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.rl_everyday:
                if (SharedPreferencesUtil.getBoolean(Objects.requireNonNull(getActivity()).getApplicationContext(),
                        "isLogin", false)) {
                    Intent intent = new Intent(getContext(), ExamActivity.class);
                    intent.putExtra("title", "每日一练");
                    intent.putExtra("id", 7);
                    intent.putExtra("num", "13");
                    getActivity().startActivity(intent);
                } else {
                    getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }
}
