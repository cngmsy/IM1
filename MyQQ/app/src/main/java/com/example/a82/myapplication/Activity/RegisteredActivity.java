package com.example.a82.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a82.myapplication.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zhy.autolayout.AutoLayoutActivity;

public class RegisteredActivity extends AutoLayoutActivity implements View.OnClickListener {

    private EditText username;
    private EditText pwd;
    private EditText pwd1;
    private Button registered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        initView();
    }

    private void initView() {
        username = (EditText) findViewById(R.id.username);
        pwd = (EditText) findViewById(R.id.pwd);
        pwd1 = (EditText) findViewById(R.id.pwd1);
        registered = (Button) findViewById(R.id.registered);

        registered.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registered:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        final String usernameString = username.getText().toString().trim();
        if (TextUtils.isEmpty(usernameString)) {
            Toast.makeText(this, "用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        final String pwdString = pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwdString)) {
            Toast.makeText(this, "密码", Toast.LENGTH_SHORT).show();
            return;
        }

        final String pwd1String = pwd1.getText().toString().trim();
        if (TextUtils.isEmpty(pwd1String)) {
            Toast.makeText(this, "确认密码", Toast.LENGTH_SHORT).show();
            return;
        }


        // TODO validate success, do something
        if (pwdString.equals(pwd1String)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().createAccount(usernameString, pwdString);
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(new Intent(RegisteredActivity.this, LoginActivity.class));
    }
}
