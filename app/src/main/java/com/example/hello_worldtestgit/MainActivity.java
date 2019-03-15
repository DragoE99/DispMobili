package com.example.hello_worldtestgit;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //test Sync con github
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view){
        EditText message_edittext = findViewById(R.id.editText);
        String message = message_edittext.getText().toString();
        message_edittext = findViewById(R.id.PhoneNumber);
        String phNumber = message_edittext.getText().toString();
        Uri uri = Uri.parse("smsto:"+phNumber);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", message);
        startActivity(it);
    }
}
