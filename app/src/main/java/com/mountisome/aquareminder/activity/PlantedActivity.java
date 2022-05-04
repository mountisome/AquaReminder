package com.mountisome.aquareminder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.utils.MySQLHelper;

public class PlantedActivity extends AppCompatActivity {

    private ImageView iv_back;
    private ImageView iv_aloe_vera;
    private ImageView iv_echinopsis_tubiflora;
    private ImageView iv_green_rose;
    private ImageView iv_pachira_macrocarpa;
    private TextView tv_aloe_vera;
    private TextView tv_echinopsis_tubiflora;
    private TextView tv_green_rose;
    private TextView tv_pachira_macrocarpa;
    private String name;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planted);
        iv_back = findViewById(R.id.iv_back);
        iv_aloe_vera = findViewById(R.id.iv_aloe_vera);
        iv_echinopsis_tubiflora = findViewById(R.id.iv_echinopsis_tubiflora);
        iv_green_rose = findViewById(R.id.iv_green_rose);
        iv_pachira_macrocarpa = findViewById(R.id.iv_pachira_macrocarpa);
        tv_aloe_vera = findViewById(R.id.tv_aloe_vera);
        tv_echinopsis_tubiflora = findViewById(R.id.tv_echinopsis_tubiflora);
        tv_green_rose = findViewById(R.id.tv_green_rose);
        tv_pachira_macrocarpa = findViewById(R.id.tv_pachira_macrocarpa);
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
                    iv_aloe_vera.setVisibility(View.VISIBLE);
                    tv_aloe_vera.setVisibility(View.VISIBLE);
                    break;
                }
                else if (i == 1) {
                    iv_echinopsis_tubiflora.setVisibility(View.VISIBLE);
                    tv_echinopsis_tubiflora.setVisibility(View.VISIBLE);
                    break;
                }
                else if (i == 2) {
                    iv_green_rose.setVisibility(View.VISIBLE);
                    tv_green_rose.setVisibility(View.VISIBLE);
                    break;
                }
                else if (i == 3) {
                    iv_pachira_macrocarpa.setVisibility(View.VISIBLE);
                    tv_pachira_macrocarpa.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    }

}