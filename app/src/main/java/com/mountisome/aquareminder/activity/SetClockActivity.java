package com.mountisome.aquareminder.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.adapter.MyAdapter;
import com.mountisome.aquareminder.adapter.MyAdapterClock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SetClockActivity extends AppCompatActivity {

    private ImageView iv_back; // 返回
    private AlarmManager alarmManager;
    private PendingIntent pi;
    private Context mContext;
    private ListView list_regular_clock;
    private ListView list_alternate_clock;
    private List<String> mRList = new ArrayList<>();
    private List<String> mAList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_clock);
        iv_back = findViewById(R.id.iv_back);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        list_regular_clock = findViewById(R.id.list_regular_clock);
        list_alternate_clock = findViewById(R.id.list_alternate_clock);
        list_regular_clock.addHeaderView(new View(this));
        list_alternate_clock.addHeaderView(new View(this));
        list_regular_clock.addFooterView(new View(this));
        list_alternate_clock.addFooterView(new View(this));
        mContext = SetClockActivity.this;

        initRegularClock();
        MyAdapter adapter1 = new MyAdapter(mContext, mRList);
        list_regular_clock.setAdapter(adapter1);
        adapter1.setOnCheckedChangeListener(new MyAdapter.onCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int i, boolean isChecked) {
                switch (i) {
                    case 0:
                        setRegularClock(540, isChecked);
                        break;
                    case 1:
                        setRegularClock(630, isChecked);
                        break;
                    case 2:
                        setRegularClock(870, isChecked);
                        break;
                    case 3:
                        setRegularClock(960, isChecked);
                        break;
                    case 4:
                        setRegularClock(1200, isChecked);
                        break;
                }
            }
        });

        initAlternateClock();
        MyAdapterClock adapter2 = new MyAdapterClock(mContext, mAList);
        list_alternate_clock.setAdapter(adapter2);
        adapter2.setOnCheckedChangeListener(new MyAdapterClock.onCheckedChangeListener() {
            @Override
            public void onCheckedChanged(int i, boolean isChecked) {
                switch (i) {
                    case 0:
                        setAlternateClock(isChecked);
                        break;
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetClockActivity.this.finish();
            }
        });
    }

    // 设置定时提醒闹钟
    public void setRegularClock(int time, boolean isChecked) {
        int hour = time / 60;
        int minute = time % 60;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        if (minute != 0) {
            calendar.set(Calendar.MINUTE, minute);
        }
        Intent intent = new Intent(SetClockActivity.this, ClockActivity.class);
        pi = PendingIntent.getActivity(SetClockActivity.this, 0, intent, 0);

        if (isChecked) {
            // 在上午9点左右唤醒设备并触发闹钟，并在每天的同一时间重复一次
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pi);
        }
        else {
            alarmManager.cancel(pi);
        }
    }

    // 设置间隔提醒闹钟
    public void setAlternateClock(boolean isChecked) {
        Intent intent = new Intent(SetClockActivity.this, ClockActivity.class);
        pi = PendingIntent.getActivity(SetClockActivity.this, 0, intent, 0);

        if (isChecked) {
            // 设置1小时后唤醒闹钟，此后每1小时触发一次
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HOUR,
                    AlarmManager.INTERVAL_HOUR, pi);

            // 设置1分钟后唤醒闹钟，为一次性闹钟
//            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                    SystemClock.elapsedRealtime() +
//                            60 * 1000, pi);
        }
        else {
            alarmManager.cancel(pi);
        }
    }

    public void initRegularClock() {
        mRList.add("09:00");
        mRList.add("10:30");
        mRList.add("14:30");
        mRList.add("16:00");
        mRList.add("20:00");
    }

    public void initAlternateClock() {
        mAList.add("间隔提醒");
    }

}