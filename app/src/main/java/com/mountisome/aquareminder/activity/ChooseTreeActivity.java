package com.mountisome.aquareminder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mountisome.aquareminder.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseTreeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv_trees;
    private SimpleAdapter simpleAdapter;
    private ImageView iv_back; // 返回
    private String name;
    private int energy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_tree);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        energy = bundle.getInt("energy");
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseTreeActivity.this.finish();
            }
        });
        lv_trees = findViewById(R.id.lv_trees);
        lv_trees.addHeaderView(new View(this));
        lv_trees.addFooterView(new View(this));
        simpleAdapter = new SimpleAdapter(ChooseTreeActivity.this, getData(),
                R.layout.tree_menu,
                new String[]{"title", "image"}, new int[]{R.id.tv_name, R.id.iv_image});
        lv_trees.setAdapter(simpleAdapter);
        lv_trees.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                Intent intent = new Intent(ChooseTreeActivity.this, ExchangeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("treeId", 0);
                bundle.putString("name", name);
                bundle.putInt("energy", energy);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(ChooseTreeActivity.this, ExchangeActivity.class);
                bundle = new Bundle();
                bundle.putInt("treeId", 1);
                bundle.putString("name", name);
                bundle.putInt("energy", energy);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(ChooseTreeActivity.this, ExchangeActivity.class);
                bundle = new Bundle();
                bundle.putInt("treeId", 2);
                bundle.putString("name", name);
                bundle.putInt("energy", energy);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(ChooseTreeActivity.this, ExchangeActivity.class);
                bundle = new Bundle();
                bundle.putInt("treeId", 3);
                bundle.putString("name", name);
                bundle.putInt("energy", energy);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    private List<Map<String, Object>> getData() {
        String[] titles = {"芦荟", "仙人球", "绿萝", "发财树"};
        int[] images = {R.drawable.aloe_vera, R.drawable.echinopsis_tubiflora,
                R.drawable.green_rose, R.drawable.pachira_macrocarpa};
        List<Map<String, Object>> list= new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", titles[i]);
            map.put("image", images[i]);
            list.add(map);
        }
        return list;
    }

}