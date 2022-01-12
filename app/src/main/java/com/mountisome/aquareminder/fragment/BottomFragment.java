package com.mountisome.aquareminder.fragment;

import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentManager;

import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.bean.User;
import com.mountisome.aquareminder.utils.DBUtils;


public class BottomFragment extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

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
    private int average_water; // 平均喝水量
    private int average_time; // 平均次数
    private boolean flag = false;
    private BroadcastReceiver dateChangeReceiver;
    private static boolean broadcastFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_fragment);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        updatePersonText();
        dateChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(Intent.ACTION_DATE_CHANGED) && broadcastFlag) {
                    broadcastFlag = false;
                    updateUserInfo();
                    flag = true;
                }
                else {
                    broadcastFlag = true;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_DATE_CHANGED);
        registerReceiver(dateChangeReceiver, intentFilter);

        fragmentManager = getFragmentManager();
        radioGroup = findViewById(R.id.rg_tab);
        radioGroup.setOnCheckedChangeListener(this);
        radioButton = findViewById(R.id.rb_drink);
        radioButton.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fragmentManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId) {
            case R.id.rb_drink:
                if (fg1 == null) {
                    fg1 = new DrinkFragment(name, water, energy);
                    fTransaction.add(R.id.ly_content, fg1);
                }
                else {
                    if (flag) {
                        fg1 = new DrinkFragment(name, 0, energy);
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
                    fg3 = new PersonFragment(name, day, average_water, average_time);
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
        Thread thread = new Thread() {
            @Override
            public void run() {
                DBUtils.updateTotalWater(name, water);
                if (water > 0) {
                    DBUtils.updateDay(name);
                }
                if (water >= 1500) {
                    DBUtils.updateEnergy(name);
                }
                DBUtils.updateWater(name);
                DBUtils.updateAverageWater(name);
                DBUtils.updateAverageTime(name);
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
                day = user.getDay();
                average_water = user.getAverage_water();
                average_time = user.getAverage_time();
            }
        };
        thread.start();
    }

}
