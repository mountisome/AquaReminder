package com.mountisome.aquareminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mountisome.aquareminder.activity.RegisterActivity;
import com.mountisome.aquareminder.bean.User;
import com.mountisome.aquareminder.fragment.BottomFragment;
import com.mountisome.aquareminder.fragment.DrinkFragment;
import com.mountisome.aquareminder.utils.DBUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String username, password;
    private EditText ed1, ed2;
    private Button btn_login, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1 = findViewById(R.id.username);
        ed2 = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 登录
            case R.id.btn_login:
                username = ed1.getText().toString();
                password = ed2.getText().toString();
                User user = new User();
                user.setName(username);
                user.setPwd(password);
                checkLogin(user);
                break;
            // 注册
            case R.id.btn_register:
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void checkLogin(User user) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                int result = DBUtils.login(user);
                if (result == 0) {
                    Looper.prepare();
                    Toast.makeText(MainActivity.this, "登录成功！",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, BottomFragment.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", user.getName());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Looper.loop();
                }
                else if (result == 1) {
                    Looper.prepare();
                    Toast.makeText(MainActivity.this, "密码错误！",
                            Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
                else {
                    Looper.prepare();
                    Toast.makeText(MainActivity.this, "用户不存在！",
                            Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        };
        thread.start();
    }

}