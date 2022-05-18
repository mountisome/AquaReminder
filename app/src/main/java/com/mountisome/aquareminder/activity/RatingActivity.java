package com.mountisome.aquareminder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.mountisome.aquareminder.R;

public class RatingActivity extends AppCompatActivity {

    private ImageView iv_back;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        iv_back = findViewById(R.id.iv_back);
        ratingBar = findViewById(R.id.ratingBar);

        // 返回
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingActivity.this.finish();
            }
        });

        // 给app打分
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(RatingActivity.this, "打分为：" + String.valueOf(rating)
                        + "，谢谢！", Toast.LENGTH_LONG).show();
            }
        });
    }

}