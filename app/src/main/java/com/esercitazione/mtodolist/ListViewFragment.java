package com.esercitazione.mtodolist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewFragment extends Fragment {
    private ListView listView = null;
    ArrayAdapter<TodoItem> adapter = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        listView = view.findViewById(R.id.list_view);
        if (adapter!= null) listView.setAdapter(adapter);
        return view;
    }

    public void setAdapter(ArrayAdapter<TodoItem> adapter){
        this.adapter = adapter;
        if (listView!= null) listView.setAdapter(adapter);
    }
}
