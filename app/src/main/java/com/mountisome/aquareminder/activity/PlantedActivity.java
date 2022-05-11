package com.mountisome.aquareminder.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.adapter.MyPagerAdapter;
import com.mountisome.aquareminder.utils.MySQLHelper;

import java.util.ArrayList;

public class PlantedActivity extends AppCompatActivity {

    private ImageView iv_back;
    private String name;
    private SQLiteDatabase db;
    private ViewPager viewPager;
    private ArrayList<View> arrayList;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planted);
        iv_back = findViewById(R.id.iv_back);
        viewPager = findViewById(R.id.pager_one);
        arrayList = new ArrayList<>();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");

        db = new MySQLHelper(this).getWritableDatabase();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlantedActivity.this.finish();
            }
        });

        plantedTree();
    }

    // 种植的树木
    public void plantedTree() {
        String planted = null;
        Cursor cursor = db.rawQuery("SELECT planted FROM user WHERE name = ?", new String[]
                {name});
        if (cursor.moveToNext()) {
            planted = cursor.getString(cursor.getColumnIndex("planted"));
        }
        cursor.close();
        if (planted == null) {
            return;
        }
        int len = planted.length();
        for (int i = 0; i < len; i++) {
            if (planted.charAt(i) == '1') {
                if (i == 0) {
                    arrayList.add(getLayoutInflater().inflate(R.layout.view_aloe_vera, null, false));
                }
                else if (i == 1) {
                    arrayList.add(getLayoutInflater().inflate(R.layout.view_echinopsis_tubiflora, null, false));
                }
                else if (i == 2) {
                    arrayList.add(getLayoutInflater().inflate(R.layout.view_green_rose, null, false));
                }
                else if (i == 3) {
                    arrayList.add(getLayoutInflater().inflate(R.layout.view_pachira_macrocarpa, null, false));
                }
            }
        }
        myPagerAdapter = new MyPagerAdapter(arrayList);
        viewPager.setAdapter(myPagerAdapter);
    }

}