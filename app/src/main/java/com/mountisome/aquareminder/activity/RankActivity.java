package com.mountisome.aquareminder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.bean.User;
import com.mountisome.aquareminder.utils.MySQLHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv_rank;
    private SimpleAdapter simpleAdapter;
    private ImageView iv_back;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        db = new MySQLHelper(this).getWritableDatabase();

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RankActivity.this.finish();
            }
        });

        lv_rank = findViewById(R.id.lv_rank);
        lv_rank.addFooterView(new View(this));
        lv_rank.addHeaderView(new View(this));
        try {
            simpleAdapter = new SimpleAdapter(RankActivity.this, getData(),
                    R.layout.rank_menu,
                    new String[]{"name", "energy"}, new int[]{R.id.tv_name, R.id.tv_energy});
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lv_rank.setAdapter(simpleAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private List<Map<String, Object>> getData() throws InterruptedException {
        String[] names = new String[100];
        String[] energies = new String[100];
        final int[] len = new int[1];

        // 查询排名
        List<User> userList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select name, energy from user order by energy desc",
                null);
        while (cursor.moveToNext()) {
            User user = new User();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int energy = cursor.getInt(cursor.getColumnIndex("energy"));
            user.setName(name);
            user.setEnergy(energy);
            userList.add(user);
        }
        cursor.close();
        len[0] = userList.size();
        for (int i = 0; i < len[0]; i++) {
            names[i] = userList.get(i).getName();
            energies[i] = String.valueOf(userList.get(i).getEnergy());
        }

        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < len[0]; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", names[i]);
            map.put("energy", energies[i]);
            list.add(map);
        }
        return list;
    }

}