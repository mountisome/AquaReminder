package com.mountisome.aquareminder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mountisome.aquareminder.MainActivity;
import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.utils.MySQLHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String username, password, mailbox;
    private EditText ed1, ed2, ed3;
    private Button btn_register;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ed1 = findViewById(R.id.username);
        ed2 = findViewById(R.id.password);
        ed3 = findViewById(R.id.mailbox);
        btn_register = findViewById(R.id.btn_register);
        db = new MySQLHelper(this).getWritableDatabase();

        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 注册
            case R.id.btn_register:
                register();
                break;
        }
    }

    // 注册
    public void register() {
        username = ed1.getText().toString();
        password = ed2.getText().toString();
        mailbox = ed3.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(mailbox)) {
            Toast.makeText(RegisterActivity.this, "请输入全部信息！",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    int result = 0;
                    // 检查用户名是否存在
                    Cursor cursor = db.rawQuery("SELECT * FROM USER WHERE name = ?", new String[]
                            {username});
                    while (cursor.moveToNext()) {
                        if (username.equals(cursor.getString(cursor.getColumnIndex("name")))) {
                            result = 1; // 用户已存在
                            break;
                        }
                    }
                    cursor.close();
                    if (result == 1) {
                        Looper.prepare();
                        Toast.makeText(RegisterActivity.this, "用户名已存在！",
                                Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {
                        // 注册
                        String sql = "insert into user(name,pwd,mailbox,water,energy,planted,day," +
                                "average_water," + "average_time,total_water,total_time) " +
                                "values(?,?,?,?,?,?,?,?,?,?,?)";
                        Object[] args = new Object[]{username, password, mailbox, 0, 0, "0000", 0, 0, 0,
                                0, 0};
                        db.execSQL(sql, args);
                        Looper.prepare();
                        Toast.makeText(RegisterActivity.this, "注册成功",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        Looper.loop();
                    }
                }
            };
            thread.start();
        }
    }

}