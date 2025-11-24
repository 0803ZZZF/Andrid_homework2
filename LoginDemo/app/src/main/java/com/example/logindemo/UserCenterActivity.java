package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class UserCenterActivity extends AppCompatActivity {
    private TextView tvUsername, tvSignature;
    private RecyclerView rvFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);

        // 初始化控件
        tvUsername = findViewById(R.id.tv_username);
        tvSignature = findViewById(R.id.tv_signature);
        rvFunction = findViewById(R.id.rv_function);

        // 读取SharedPreferences中的用户信息
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = sp.getString("username", "用户");
        String signature = sp.getString("signature", "欢迎来到信息App");
        tvUsername.setText(username);
        tvSignature.setText(signature);

        // 初始化功能列表数据
        List<FunctionBean> functionList = new ArrayList<>();
        functionList.add(new FunctionBean(R.drawable.collection_icon, "我的收藏"));
        functionList.add(new FunctionBean(R.drawable.history_icon, "浏览历史"));
        functionList.add(new FunctionBean(R.drawable.setting_icon, "设置"));
        functionList.add(new FunctionBean(R.drawable.about_us, "关于我们")); // 注意素材名要和实际一致
        functionList.add(new FunctionBean(R.drawable.feedback, "意见反馈"));

        // 设置RecyclerView
        rvFunction.setLayoutManager(new LinearLayoutManager(this));
        rvFunction.setAdapter(new FunctionAdapter(functionList));
    }
}