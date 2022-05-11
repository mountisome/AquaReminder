package com.mountisome.aquareminder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.utils.MySQLHelper;

public class ExchangeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int ENERGY_1 = 5;
    private static final int ENERGY_2 = 7;
    private static final int ENERGY_3 = 10;

    private ImageView iv_back; // 返回
    private Button btn_exchange; // 兑换按钮
    private ImageView iv_aloe_vera; // 芦荟
    private ImageView iv_echinopsis_tubiflora; // 仙人球
    private ImageView iv_green_rose; // 绿萝
    private ImageView iv_pachira_macrocarpa; // 发财树
    private TextView tv_energy_need; // 兑换树木所需能量
    private TextView tv_energy_need1; // 兑换树木所需能量1
    private TextView tv_energy_need2; // 兑换树木所需能量2
    private String name;
    private int energy;
    private int treeId; // 树的序号
    private String planted;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        treeId = bundle.getInt("treeId");
        name = bundle.getString("name");
        energy = bundle.getInt("energy");
        iv_back = findViewById(R.id.iv_back);
        btn_exchange = findViewById(R.id.btn_exchange);
        iv_aloe_vera = findViewById(R.id.iv_aloe_vera);
        iv_echinopsis_tubiflora = findViewById(R.id.iv_echinopsis_tubiflora);
        iv_green_rose = findViewById(R.id.iv_green_rose);
        iv_pachira_macrocarpa = findViewById(R.id.iv_pachira_macrocarpa);
        tv_energy_need = findViewById(R.id.tv_energy_need);
        tv_energy_need1 = findViewById(R.id.tv_energy_need1);
        tv_energy_need2 = findViewById(R.id.tv_energy_need2);

        db = new MySQLHelper(this).getWritableDatabase();

        if (treeId == 0) {
            iv_aloe_vera.setVisibility(View.VISIBLE);
            tv_energy_need.setVisibility(View.VISIBLE);
        }
        else if (treeId == 1) {
            iv_echinopsis_tubiflora.setVisibility(View.VISIBLE);
            tv_energy_need.setVisibility(View.VISIBLE);
        }
        else if (treeId == 2) {
            iv_green_rose.setVisibility(View.VISIBLE);
            tv_energy_need1.setVisibility(View.VISIBLE);
        }
        else if (treeId == 3) {
            iv_pachira_macrocarpa.setVisibility(View.VISIBLE);
            tv_energy_need2.setVisibility(View.VISIBLE);
        }
        iv_back.setOnClickListener(this);
        btn_exchange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                ExchangeActivity.this.finish();
                break;
            case R.id.btn_exchange:
                exchangeTree();
                break;
        }
    }

    public void exchangeTree() {
        Cursor cursor = db.rawQuery("SELECT planted FROM user WHERE name = ?", new String[]
                {name});
        if (cursor.moveToNext()) {
            planted = cursor.getString(cursor.getColumnIndex("planted"));
        }
        cursor.close();

        // 如果已拥有某个树木，则无法兑换
        if (planted.charAt(treeId) == '1')
            Toast.makeText(ExchangeActivity.this, "已拥有该树木，无法兑换！",
                    Toast.LENGTH_SHORT).show();
        else if ((treeId == 0 || treeId == 1) && energy >= ENERGY_1) {
            updateTree(name, ENERGY_1, treeId);
            exchangeToBottom();
        }
        else if (treeId == 2 && energy >= ENERGY_2) {
            updateTree(name, ENERGY_2, treeId);
            exchangeToBottom();
        }
        else if (treeId == 3 && energy >= ENERGY_3) {
            updateTree(name, ENERGY_3, treeId);
            exchangeToBottom();
        }
        else {
            Toast.makeText(ExchangeActivity.this, "兑换树木的能量不足~",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // 兑换树木
    public void updateTree(String name, int energy, int treeId) {
        String sql = "update user set energy = energy - ? where name = ?";
        Object[] args = new Object[]{energy, name};
        db.execSQL(sql, args);
        StringBuilder stringBuilder = new StringBuilder(planted);
        stringBuilder.setCharAt(treeId, '1');
        planted = stringBuilder.toString();
        sql = "update user set planted = ? where name = ?";
        args = new Object[]{planted, name};
        db.execSQL(sql, args);
    }

    // 跳转页面
    public void exchangeToBottom() {
        Intent intent = new Intent(ExchangeActivity.this, BottomActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}