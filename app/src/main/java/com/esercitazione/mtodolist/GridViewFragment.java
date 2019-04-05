package com.esercitazione.mtodolist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class GridViewFragment extends Fragment {
    private GridView gridView = null;
    ArrayAdapter<TodoItem> adapter = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gridview, container, false);
        gridView = view.findViewById(R.id.grid_view);
        if (adapter!= null) gridView.setAdapter(adapter);
        return view;
    }

    public void setAdapter(ArrayAdapter<TodoItem> adapter){
        this.adapter = adapter;
        if (gridView!= null) gridView.setAdapter(adapter);
    }
}
