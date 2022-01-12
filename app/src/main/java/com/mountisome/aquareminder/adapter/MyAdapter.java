package com.mountisome.aquareminder.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mountisome.aquareminder.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList = new ArrayList<>();

    public MyAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.clock_menu, null);
            viewHolder.mTextView = view.findViewById(R.id.tv_clock);
            viewHolder.mSwitchCompat = view.findViewById(R.id.sc_clock);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mTextView.setText(mList.get(i));
        viewHolder.mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                monCheckedChangeListener.onCheckedChanged(i, isChecked);
            }
        });
        return view;
    }

    public interface onCheckedChangeListener {
        void onCheckedChanged(int i, boolean isChecked);
    }

    private onCheckedChangeListener monCheckedChangeListener;

    public void setOnCheckedChangeListener(onCheckedChangeListener monCheckedChangeListener) {
        this.monCheckedChangeListener = monCheckedChangeListener;
    }

    class ViewHolder {
        TextView mTextView;
        SwitchCompat mSwitchCompat;
    }

}
