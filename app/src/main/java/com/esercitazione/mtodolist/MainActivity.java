package com.esercitazione.mtodolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Boolean gridOrList=false;

        if(gridOrList) {
            setContentView(R.layout.activity_main);


            final ListView listView = findViewById(R.id.list_view);
            final EditText editText = findViewById(R.id.texttodo);
            final Button addButton = findViewById(R.id.addtodobutton);

            final ArrayList<TodoItem> todoItems = new ArrayList<>();

            final ArrayAdapter<TodoItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoItems);

            listView.setAdapter(adapter);
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
        }else{
            setContentView(R.layout.activity_main_grid);

            final GridView listView = findViewById(R.id.grid_view);
            final EditText editText = findViewById(R.id.texttodo);
            final Button addButton = findViewById(R.id.addtodobutton);

            final ArrayList<TodoItem> todoItems = new ArrayList<>();

            final ArrayAdapter<TodoItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoItems);

            listView.setAdapter(adapter);
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

    }

}

class TodoItem {
    private String todo;
    private GregorianCalendar createOn;

    public TodoItem(String todo){
        super();
        this.todo = todo;
        this.createOn = new GregorianCalendar();

    }
    @Override
    public String toString(){
        String currentDate = new SimpleDateFormat("dd/MM/yyyy",
                Locale.ITALIAN).format(createOn.getTime());
        return currentDate + ":\n>> "+ todo;
    }
}


