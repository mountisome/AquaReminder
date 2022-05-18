package com.mountisome.aquareminder.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.activity.PersonActivity;
import com.mountisome.aquareminder.activity.RankActivity;
import com.mountisome.aquareminder.activity.RatingActivity;
import com.mountisome.aquareminder.utils.MySQLHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String name;
    private int day; // 喝水天数
    private int average_water; // 平均喝水量
    private int average_time; // 平均次数
    private ImageView iv_picture; // 头像
    private TextView tv_day; // 喝水天数
    private TextView tv_average_water; // 平均喝水量
    private TextView tv_average_time; // 平均次数
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private AlertDialog.Builder builder = null;
    private SQLiteDatabase db;

    public PersonFragment(String name) {
        this.name = name;
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

        iv_picture = view.findViewById(R.id.iv_picture);

        TextView fg_person = view.findViewById(R.id.fg_person);
        fg_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        fg_person.setText(name);

        db = new MySQLHelper(getActivity()).getWritableDatabase();
        updatePersonText();

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
                intent = new Intent(getActivity(), RatingActivity.class);
                startActivity(intent);
                break;
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

    // 更新数据
    public void updatePersonText() {
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE name = ?", new String[]
                {name});
        if (cursor.moveToNext()) {
            day = cursor.getInt(cursor.getColumnIndex("day"));
            average_water = cursor.getInt(cursor.getColumnIndex("average_water"));
            average_time = cursor.getInt(cursor.getColumnIndex("average_time"));
        }
        cursor.close();
    }

}