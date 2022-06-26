package com.mountisome.aquareminder.activity;

import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentManager;

import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.fragment.DrinkFragment;
import com.mountisome.aquareminder.fragment.PersonFragment;
import com.mountisome.aquareminder.fragment.TreeFragment;
import com.mountisome.aquareminder.utils.MySQLHelper;
import com.mountisome.aquareminder.utils.SQLCon;


public class BottomActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private DrinkFragment fg1;
    private TreeFragment fg2;
    private PersonFragment fg3;
    private FragmentManager fragmentManager;
    private String name; // 用户名
    private int water; // 饮水量
    private int energy; // 能量
    private int day; // 喝水天数
    private int total_water; // 总喝水量
    private int total_time; // 总次数
    private boolean flag = false;
    private SQLiteDatabase db;
    private BroadcastReceiver dateChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        db = new MySQLHelper(this).getWritableDatabase();

        updatePersonText();

        fragmentManager = getFragmentManager();
        radioGroup = findViewById(R.id.rg_tab);
        radioGroup.setOnCheckedChangeListener(this);
        radioButton = findViewById(R.id.rb_drink);
        radioButton.setChecked(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dateChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(Intent.ACTION_DATE_CHANGED)) {
                    flag = true;
                    updateUserInfo();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_DATE_CHANGED);
        registerReceiver(dateChangeReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(dateChangeReceiver);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId) {
            case R.id.rb_drink:
                if (fg1 == null) {
                    fg1 = new DrinkFragment(water, name);
                    fTransaction.add(R.id.ly_content, fg1);
                }
                else {
                    if (flag) {
                        fg1 = new DrinkFragment(0, name);
                        fTransaction.add(R.id.ly_content, fg1);
                        flag = false;
                    }
                    fTransaction.show(fg1);
                }
                break;
            case R.id.rb_tree:
                if (fg2 == null) {
                    fg2 = new TreeFragment(name, energy);
                    fTransaction.add(R.id.ly_content, fg2);
                }
                else {
                    fTransaction.show(fg2);
                }
                break;
            case R.id.rb_person:
                if(fg3 == null) {
                    fg3 = new PersonFragment(name);
                    fTransaction.add(R.id.ly_content, fg3);
                }
                else {
                    fTransaction.show(fg3);
                }
                break;
        }
        fTransaction.commit();
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if(fg1 != null) fragmentTransaction.hide(fg1);
        if(fg2 != null) fragmentTransaction.hide(fg2);
        if(fg3 != null) fragmentTransaction.hide(fg3);
    }

    public void updateUserInfo() {
        Object[] args = new Object[]{name};
        if (water > 0) {
            String sql = "update user set day = day + 1 where name = ?";
            db.execSQL(sql, args);
        }
        if (water >= 1500) {
            String sql2 = "update user set energy = energy + 1 where name = ?";
            db.execSQL(sql2, args);
        }
        args = new Object[]{0, name};
        String sql3 = "update user set water = ? where name = ?";
        db.execSQL(sql3, args);
        // 更新平均喝水量
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE name = ?", new String[]
                {name});
        if (cursor.moveToNext()) {
            day = cursor.getInt(cursor.getColumnIndex("day"));
            total_water = cursor.getInt(cursor.getColumnIndex("total_water"));
            total_time = cursor.getInt(cursor.getColumnIndex("total_time"));
        }
        String sql4 = "update user set average_water = ? where name = ?";
        Object[] args2 = new Object[]{total_water / day, name};
        db.execSQL(sql4, args2);
        // 更新平均次数
        String sql5 = "update user set average_time = ? where name = ?";
        Object[] args3 = new Object[]{total_time / day, name};
        db.execSQL(sql5, args3);
        cursor.close();
    }

    // 更新数据
    public void updatePersonText() {
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE name = ?", new String[]
                {name});
        if (cursor.moveToNext()) {
            water = cursor.getInt(cursor.getColumnIndex("water"));
            energy = cursor.getInt(cursor.getColumnIndex("energy"));
            day = cursor.getInt(cursor.getColumnIndex("day"));
        }
        cursor.close();
    }

}
