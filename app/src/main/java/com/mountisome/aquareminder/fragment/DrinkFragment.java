package com.mountisome.aquareminder.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.activity.AddActivity;
import com.mountisome.aquareminder.activity.FitActivity;
import com.mountisome.aquareminder.activity.SetClockActivity;
import com.mountisome.aquareminder.bean.User;
import com.mountisome.aquareminder.utils.DBUtils;
import com.mountisome.aquareminder.utils.Histogram;

public class DrinkFragment extends Fragment implements View.OnClickListener {

    private final int MAX_WATER = 1800;

    private String name; // 用户名
    private int water; // 饮水量
    private int energy; // 能量
    private ImageView iv_fit; // 设置
    private ImageView iv_clock; // 闹钟
    private Button btn_add; // 添加
    private Histogram histogram;
    private int tempWater;

    public DrinkFragment() {
    }

    @SuppressLint("ValidFragment")
    public DrinkFragment(String name, int water, int energy) {
        this.name = name;
        this.water = water;
        this.energy = energy;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink, container, false);
        iv_fit = view.findViewById(R.id.iv_fit);
        iv_clock = view.findViewById(R.id.iv_clock);
        btn_add = view.findViewById(R.id.btn_add);
        histogram = view.findViewById(R.id.bottle);

        iv_fit.setOnClickListener(this);
        iv_clock.setOnClickListener(this);
        btn_add.setOnClickListener(this);

        tempWater = water;
        if (tempWater >= 1500) {
            tempWater = 1500;
        }
        updatePersonText();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (water >= 1500) {
            water = 1500;
        }

        histogram.setData(tempWater, water, MAX_WATER);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fit:
                Intent intent = new Intent(getActivity(), FitActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_clock:
                intent = new Intent(getActivity(), SetClockActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_add:
                intent = new Intent(getActivity(), AddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
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
