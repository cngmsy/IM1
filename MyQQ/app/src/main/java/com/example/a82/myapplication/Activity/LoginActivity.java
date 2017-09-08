package com.example.a82.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a82.myapplication.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zhy.autolayout.AutoLayoutActivity;

public class LoginActivity extends AutoLayoutActivity implements View.OnClickListener {

    private EditText username;
    private EditText pwd;
    private Button registered;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        username = (EditText) findViewById(R.id.username);
        pwd = (EditText) findViewById(R.id.pwd);
        registered = (Button) findViewById(R.id.registered);
        login = (Button) findViewById(R.id.login);

        registered.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registered:
                startActivity(new Intent(LoginActivity.this, RegisteredActivity.class));
                break;
            case R.id.login:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String usernameString = username.getText().toString().trim();
        String pwdString = pwd.getText().toString().trim();
        if (TextUtils.isEmpty(usernameString) || TextUtils.isEmpty(pwdString)) {
            Toast.makeText(this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        EMClient.getInstance().login(usernameString, pwdString, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                Log.e("LoginActivity", "登陆成功");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.e("LoginActivity", code+message);
            }
        });

    }
}
