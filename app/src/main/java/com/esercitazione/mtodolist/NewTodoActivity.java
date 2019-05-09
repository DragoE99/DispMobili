package com.esercitazione.mtodolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;


public class NewTodoActivity extends AppCompatActivity {
    EditText editText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        editText=findViewById(R.id.newTodoEditText);
        //set the home button
        Toolbar myToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                //button go back activity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("TODO_TASK",editText.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
