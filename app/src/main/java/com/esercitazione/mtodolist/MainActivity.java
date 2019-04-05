package com.esercitazione.mtodolist;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ListViewFragment listViewFragment = new ListViewFragment();
    GridViewFragment gridViewFragment = new GridViewFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, gridViewFragment);
        fragmentTransaction.commit();

        final EditText editText = findViewById(R.id.texttodo);
        final Button ListButton = findViewById(R.id.ListButton);
        final Button GridButton = findViewById(R.id.GridButton);
        final Button addButton = findViewById(R.id.addtodobutton);

        final ArrayList<TodoItem> todoItems = new ArrayList<>();

        final ArrayAdapter<TodoItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoItems);

        listViewFragment.setAdapter(adapter);
        gridViewFragment.setAdapter(adapter);

        ListButton.setOnClickListener(this);
        GridButton.setOnClickListener(this);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todo = editText.getText().toString();
                if (todo.length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Empty ToDo string",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                TodoItem newTodo = new TodoItem(todo);
                todoItems.add(0, newTodo);
                adapter.notifyDataSetChanged();
                editText.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getApplicationWindowToken(), 0);
                editText.clearFocus();

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
}


