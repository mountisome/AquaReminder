package com.mountisome.aquareminder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.utils.MySQLHelper;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private String name; // 用户名
    private int water; // 饮水量
    private EditText ed_water; // 饮水量输入框
    private Button tv_finish; // 完成按钮
    private Button btn_50;
    private Button btn_100;
    private Button btn_200;
    private TextView iv_back; // 取消
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        db = new MySQLHelper(this).getWritableDatabase();

        updatePersonText();

        ed_water = findViewById(R.id.ed_water);
        tv_finish = findViewById(R.id.tv_finish);
        iv_back = findViewById(R.id.iv_back);
        btn_50 = findViewById(R.id.btn_50);
        btn_100 = findViewById(R.id.btn_100);
        btn_200 = findViewById(R.id.btn_200);

        btn_50.setOnClickListener(this);
        btn_100.setOnClickListener(this);
        btn_200.setOnClickListener(this);
        tv_finish.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_finish:
                if (TextUtils.isEmpty(ed_water.getText())) {
                    Toast.makeText(AddActivity.this, "饮水量不可为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(ed_water.getText().toString()) > 1500 ||
                        Integer.parseInt(ed_water.getText().toString()) < 0) {
                    Toast.makeText(AddActivity.this, "饮水量不符合实际",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                water += Integer.parseInt(ed_water.getText().toString());
                addWater(water);
                updateTotalTime();
                Intent intent = new Intent(AddActivity.this, BottomActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.iv_back:
                AddActivity.this.finish();
                break;
            case R.id.btn_50:
                ed_water.setText("50");
                break;
            case R.id.btn_100:
                ed_water.setText("100");
                break;
            case R.id.btn_200:
                ed_water.setText("200");
                break;
        }
    }

    // 添加水量
    public void addWater(int water) {
        String sql = "update user set water = ? where name = ?";
        Object[] args = new Object[]{water, name};
        db.execSQL(sql, args);
    }

    // 更新喝水总次数
    public void updateTotalTime() {
        String sql = "update user set total_time = total_time + 1 where name = ?";
        Object[] args = new Object[]{name};
        db.execSQL(sql, args);
    }

    // 更新数据
    public void updatePersonText() {
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE name = ?", new String[]
                {name});
        if (cursor.moveToNext()) {
            water = cursor.getInt(cursor.getColumnIndex("water"));
        }
        cursor.close();
    }

}