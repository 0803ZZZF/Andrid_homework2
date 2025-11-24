package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmail, etPwd;
    private Button btnLogin, btnWechat, btnApple;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 初始化控件
        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnWechat = findViewById(R.id.btn_wechat_login);
        btnApple = findViewById(R.id.btn_apple_login);

        // 设置点击事件
        btnLogin.setOnClickListener(this);
        btnWechat.setOnClickListener(this);
        btnApple.setOnClickListener(this);

        // 初始化数据库
        dbHelper = new MyDatabaseHelper(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            // 处理登录逻辑
            login();
        } else if (v.getId() == R.id.btn_wechat_login) {
            Toast.makeText(this, "微信登录功能暂未开放", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.btn_apple_login) {
            Toast.makeText(this, "Apple登录功能暂未开放", Toast.LENGTH_SHORT).show();
        }
    }

    private void login() {
        // 获取输入的邮箱和密码
        String email = etEmail.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();

        // 非空校验
        if (email.isEmpty()) {
            Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // 查询数据库，验证账号密码
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                MyDatabaseHelper.TABLE_USER,
                new String[]{MyDatabaseHelper.COLUMN_PWD},
                MyDatabaseHelper.COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null
        );

        boolean isSuccess = false;
        if (cursor.moveToFirst()) {
            String dbPwd = cursor.getString(0);
            if (dbPwd.equals(pwd)) {
                isSuccess = true;
            }
        }
        cursor.close();
        db.close();

        if (isSuccess) {
            // 登录成功：存储用户信息到SharedPreferences
            SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
            sp.edit()
                    .putString("username", email.split("@")[0]) // 取邮箱前缀当用户名
                    .putString("signature", "欢迎来到信息App")
                    .apply();

            // 跳转到个人中心页
            startActivity(new Intent(LoginActivity.this, UserCenterActivity.class));
            finish(); // 关闭当前页
        } else {
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}