package com.esercitazione.mtodolist;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    ListViewFragment listViewFragment = new ListViewFragment();
    GridViewFragment gridViewFragment = new GridViewFragment();
    private ArrayList<TodoItem> todoItems;
    private ArrayAdapter<TodoItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, gridViewFragment);
        fragmentTransaction.commit();

        final EditText editText = findViewById(R.id.texttodo);
        final Button ListButton = findViewById(R.id.ListButton);
        final Button GridButton = findViewById(R.id.GridButton);
        final Button addButton = findViewById(R.id.addtodobutton);

        todoItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoItems);

        listViewFragment.setAdapter(adapter);
        gridViewFragment.setAdapter(adapter);

        listViewFragment.setTodoItems(todoItems);
        gridViewFragment.setTodoItems(todoItems);

        ListButton.setOnClickListener(this);
        GridButton.setOnClickListener(this);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddItem(editText);

            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.ListButton)) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, listViewFragment);
            fragmentTransaction.commit();

        } else if (v == findViewById(R.id.GridButton)){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, gridViewFragment);
            fragmentTransaction.commit();
        }
    }

    public void onAddItem(EditText editText) {
        String todo = editText.getText().toString();
        if (todo.length()==0) {
            Toast.makeText(getApplicationContext(),
                    "Empty ToDo string",
                    Toast.LENGTH_LONG).show();
            return;
        }
        TodoItem newTodo = new TodoItem(todo);
        todoItems.add(0, newTodo);
        DatabaseHelper.getInstance(this).insertItem(newTodo);
        adapter.notifyDataSetChanged();
        editText.setText("");
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);
        editText.clearFocus();
    }


    @Override protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()");
        this.todoItems.clear();
        todoItems.addAll(DatabaseHelper.getInstance(this).getAllItems());
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Not yet implemented!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_new_item) {
            Toast.makeText(getApplicationContext(), "Not yet implemented!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


