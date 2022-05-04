package com.mountisome.aquareminder.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mountisome.aquareminder.R;
import com.mountisome.aquareminder.activity.ChooseTreeActivity;
import com.mountisome.aquareminder.activity.PlantedActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView list_tree;
    private SimpleAdapter simpleAdapter;
    private String name;
    private int energy;

    public TreeFragment(String name, int energy) {
        this.name = name;
        this.energy = energy;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tree, container, false);
        list_tree = view.findViewById(R.id.list_tree);
        simpleAdapter = new SimpleAdapter(getActivity(), getData(), R.layout.tree_menu,
                new String[]{"title", "image"}, new int[]{R.id.tv_name, R.id.iv_image});
        list_tree.setAdapter(simpleAdapter);
        list_tree.addHeaderView(new View(getActivity()));
        list_tree.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                Intent intent = new Intent(getActivity(), ChooseTreeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putInt("energy", energy);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(getActivity(), PlantedActivity.class);
                bundle = new Bundle();
                bundle.putString("name", name);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    private List<Map<String, Object>> getData() {
        String[] titles = {"选择树木", "已种植"};
        int[] images = {R.drawable.tree, R.drawable.planted};
        List<Map<String, Object>> list= new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", titles[i]);
            map.put("image", images[i]);
            list.add(map);
        }
        return list;
    }

}
