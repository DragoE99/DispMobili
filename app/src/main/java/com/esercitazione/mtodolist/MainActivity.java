package com.esercitazione.mtodolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final int SUBACTIVITY_NEW_TODO_ITEM = 1;
    private static final String TAG = "MainActivity";
    ListViewFragment listViewFragment = new ListViewFragment();
    GridViewFragment gridViewFragment = new GridViewFragment();
    private ArrayList<TodoItem> todoItems;
    private ArrayAdapter<TodoItem> adapter;


    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, gridViewFragment);
        fragmentTransaction.commit();

        //final EditText editText = findViewById(R.id.texttodo);
        final Button ListButton = findViewById(R.id.ListButton);
        final Button GridButton = findViewById(R.id.GridButton);
        //final Button addButton = findViewById(R.id.addtodobutton);

        todoItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, todoItems);

        listViewFragment.setAdapter(adapter);
        gridViewFragment.setAdapter(adapter);

        listViewFragment.setTodoItems(todoItems);
        gridViewFragment.setTodoItems(todoItems);

        ListButton.setOnClickListener(this);
        GridButton.setOnClickListener(this);
       /* addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddItem(editText);

            }
        });*/

        // Initialize Firebase Auth and Database Reference
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
// Not logged in, launch the activity
            loadLogInView();
        }else {
            mUserId = mFirebaseUser.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }


    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.ListButton)) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, listViewFragment);
            fragmentTransaction.commit();

        } else if (v == findViewById(R.id.GridButton)) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, gridViewFragment);
            fragmentTransaction.commit();
        }
    }

    public void onAddItem(String todo) {
        if (todo.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Empty ToDo string",
                    Toast.LENGTH_LONG).show();
            return;
        }
        TodoItem newTodo = new TodoItem(todo);
        todoItems.add(0, newTodo);
        DatabaseHelper.getInstance(this).insertItem(newTodo);
        adapter.notifyDataSetChanged();

    }


    @Override
    protected void onResume() {
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
            onMenuItemClick(item);
            return true;
        } else if (id == R.id.action_new_item) {
            onMenuItemClick(item);
            return true;
        }else if (id == R.id.action_signout) {
            Log.d(TAG,"action SignOut clicked");
            FirebaseAuth.getInstance().signOut();
            mFirebaseUser = null; // user is now signed out
            startActivity(new Intent(MainActivity.this, LogInActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_item:
                Log.d(TAG, "action ADD has clicked");
                // Explicit intent creation
                Intent intent = new Intent(this.getApplicationContext(), NewTodoActivity.class);

                //Start as sub activity for result
                startActivityForResult(intent, SUBACTIVITY_NEW_TODO_ITEM);
                return true;
            case R.id.action_settings:
                Log.d(TAG, "action SETTINGS has clicked");
                return true;

           /* case R.id.action_help:
                Log.d(TAG,"action HELP has clicked");
                return true;*/

        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() ->" + data);
        switch (requestCode) {
            case SUBACTIVITY_NEW_TODO_ITEM:
                switch (resultCode) {
                    case Activity
                            .RESULT_OK:
                        //ADD NEW todoItem
                        String returnValue = data.getStringExtra("TODO_TASK");
                        Log.d(TAG, "onActivityResult() ->" + returnValue);
                        onAddItem(returnValue);
                        return;
                    case Activity.RESULT_CANCELED:
                        return;
                    default:
                        throw new RuntimeException("case not implemented");
                }

            default:
                throw new RuntimeException();
        }
    }


    private void loadLogInView() {

        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}


