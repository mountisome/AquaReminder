package com.mountisome.aquareminder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.utils.MySQLHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonActivity extends AppCompatActivity {

    private ImageView iv_back;
    private ListView lv_info;
    private SimpleAdapter simpleAdapter;
    private String name;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        iv_back = findViewById(R.id.iv_back);
        lv_info = findViewById(R.id.lv_info);
        db = new MySQLHelper(this).getWritableDatabase();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonActivity.this.finish();
            }
        });

        lv_info.addHeaderView(new View(this));
        lv_info.addFooterView(new View(this));
        simpleAdapter = new SimpleAdapter(PersonActivity.this, getData(),
                R.layout.info_menu,
                new String[]{"info", "data"}, new int[]{R.id.tv_info, R.id.tv_data});
        lv_info.setAdapter(simpleAdapter);
    }

    private List<Map<String, String>> getData() {
        int day = 0, average_water = 0, average_time = 0, energy = 0, total_water = 0, total_time = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE name = ?", new String[]
                {name});
        if (cursor.moveToNext()) {
            day = cursor.getInt(cursor.getColumnIndex("day"));
            average_water = cursor.getInt(cursor.getColumnIndex("average_water"));
            average_time = cursor.getInt(cursor.getColumnIndex("average_time"));
            energy = cursor.getInt(cursor.getColumnIndex("energy"));
            total_water = cursor.getInt(cursor.getColumnIndex("total_water"));
            total_time = cursor.getInt(cursor.getColumnIndex("total_time"));
        }
        cursor.close();
        String[] infos = {"名字", "喝水天数", "平均喝水量", "平均次数", "能量", "总喝水量", "总次数"};
        String[] datas = {name, String.valueOf(day), String.valueOf(average_water), String.valueOf(average_time),
                String.valueOf(energy), String.valueOf(total_water), String.valueOf(total_time)};
        List<Map<String, String>> list= new ArrayList<>();
        for (int i = 0; i < infos.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("info", infos[i]);
            map.put("data", datas[i]);
            list.add(map);
        }
        return list;
    }
}