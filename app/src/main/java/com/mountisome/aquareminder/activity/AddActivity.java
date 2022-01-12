package com.mountisome.aquareminder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.bean.User;
import com.mountisome.aquareminder.fragment.BottomFragment;
import com.mountisome.aquareminder.utils.DBUtils;
import com.mountisome.aquareminder.utils.Histogram;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private String name; // 用户名
    private int water; // 饮水量
    private int energy; // 能量
    private EditText ed_water; // 饮水量输入框
    private Button tv_finish; // 完成按钮
    private Button btn_50;
    private Button btn_100;
    private Button btn_200;
    private TextView iv_back; // 取消

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");

        updatePersonText();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
                Intent intent = new Intent(AddActivity.this, BottomFragment.class);
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

    public void addWater(int water) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                DBUtils.addWater(name, water);
                Toast.makeText(AddActivity.this, "添加成功",
                        Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        };
        thread.start();
    }

    public void updateTotalTime() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                DBUtils.updateTotalTime(name);
            }
        };
        thread.start();
    }

    public void updatePersonText() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                User user = DBUtils.queryUser(name);
                water = user.getWater();
                energy = user.getEnergy();
            }
        };
        thread.start();
    }

}