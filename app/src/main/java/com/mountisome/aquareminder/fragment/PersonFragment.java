package com.mountisome.aquareminder.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.activity.ChooseTreeActivity;
import com.mountisome.aquareminder.activity.FitActivity;
import com.mountisome.aquareminder.activity.RankActivity;
import com.mountisome.aquareminder.bean.User;
import com.mountisome.aquareminder.utils.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String name;
    private int day; // 喝水天数
    private int average_water; // 平均喝水量
    private int average_time; // 平均次数
    private TextView tv_day; // 喝水天数
    private TextView tv_average_water; // 平均喝水量
    private TextView tv_average_time; // 平均次数
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;

    public PersonFragment() {
    }

    @SuppressLint("ValidFragment")
    public PersonFragment(String name, int day, int average_water, int average_time) {
        this.name = name;
        this.day = day;
        this.average_water = average_water;
        this.average_time = average_time;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        listView = view.findViewById(R.id.list_function);
        tv_day = view.findViewById(R.id.tv_day);
        tv_average_water = view.findViewById(R.id.tv_average_water);
        tv_average_time = view.findViewById(R.id.tv_average_time);
        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.person_menu,
                new String[]{"title", "image"}, new int[]{R.id.tv_name, R.id.iv_image});
        listView.setAdapter(simpleAdapter);
        listView.addHeaderView(new View(getActivity()));
        listView.setOnItemClickListener(this);
        TextView fg_person = view.findViewById(R.id.fg_person);
        fg_person.setText(name);

        updatePersonText();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tv_day.setText(Integer.toString(day));
        tv_average_water.setText(Integer.toString(average_water));
        tv_average_time.setText(Integer.toString(average_time));
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                Intent intent = new Intent(getActivity(), RankActivity.class);
                startActivity(intent);
                break;
            case 2:
                alert = null;
                builder = new AlertDialog.Builder(getActivity());
                alert = builder.setTitle("给个好评")
                        .setMessage("开发中，敬请期待！").setNegativeButton("好的",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).create();
                alert.show();
        }
    }

    private List<Map<String, Object>> getData() {
        String[] titles = {"排名", "给个好评"};
        int[] images = {R.drawable.rank, R.drawable.heart};
        List<Map<String, Object>> list= new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", titles[i]);
            map.put("image", images[i]);
            list.add(map);
        }
        return list;
    }

    public void updatePersonText() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                User user = DBUtils.queryUser(name);
                day = user.getDay();
                average_water = user.getAverage_water();
                average_time = user.getAverage_time();
            }
        };
        thread.start();
    }

}