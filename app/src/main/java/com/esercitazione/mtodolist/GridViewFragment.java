package com.esercitazione.mtodolist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class GridViewFragment extends Fragment {
    private GridView gridView = null;
    ArrayAdapter<TodoItem> adapter = null;
    private ArrayList<TodoItem> todoItems=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gridview, container, false);
        gridView = view.findViewById(R.id.grid_view);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long itemId) {
                onLongClick(position);
                return true;
            }
        });
        if (adapter!= null) gridView.setAdapter(adapter);
        return view;
    }

    public void setAdapter(ArrayAdapter<TodoItem> adapter){
        this.adapter = adapter;
        if (gridView!= null) gridView.setAdapter(adapter);
    }
    public void setTodoItems(ArrayList<TodoItem> todoItems){
        this.todoItems = todoItems;
    }

    private void onLongClick(final int position) {
        TodoItem item = (TodoItem) gridView.getItemAtPosition(position);
        Toast.makeText(getActivity(), "Deleted Item " + position + " " + item.toString(), Toast.LENGTH_LONG).show();
        DatabaseHelper.getInstance(getActivity()).deleteItem(item);

        todoItems.remove(position);
        adapter.notifyDataSetChanged();
    }
}
