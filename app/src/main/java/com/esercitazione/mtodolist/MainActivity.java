package com.esercitazione.mtodolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<TodoItem> todoItems;
    private ArrayAdapter<TodoItem> listViewAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);


        final ListView listView = findViewById(R.id.list_view);



        todoItems = new ArrayList<>();

        listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoItems);

        listView.setAdapter(listViewAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long itemId) {
                onLongClick(position, listView);
                return true;
            } });
    }


    private void onLongClick(final int position, final ListView listView) {

        TodoItem item = (TodoItem) listView.getItemAtPosition(position);
        Toast.makeText(getApplicationContext(),
                "Deleted Item " + position + " " + item.toString(),
                Toast.LENGTH_LONG).show();

        dbHelper.deleteItem(item);

        todoItems.remove(position);

        listViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");
        this.todoItems.clear();
        todoItems.addAll(dbHelper.getAllItems());
        listViewAdapter.notifyDataSetChanged();
    }

    public void onAddItem(View v) {
        EditText editText = (EditText) findViewById(R.id.input_insert_todo);
        TodoItem item = new TodoItem(editText.getText().toString());
        todoItems.add(0, item); listViewAdapter.notifyDataSetChanged();
        long idx = dbHelper.insertItem(item); item.setId(idx);
        editText.setText("");
        InputMethodManager imm = (InputMethodManager) getSystemService( MainActivity.this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);
        editText.clearFocus();
    }
}



