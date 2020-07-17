package ru.wtw.moreliatalkclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity  {
    private Network network;
    public boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isConnected=false;
        network = new Network(MainActivity.this);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Log.i("SERVER","Extra");
            network.setUsername(extras.getString("username"));
            network.setPassword(extras.getString("password"));
            network.setServername(extras.getString("servername"));
            network.connect();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView chat = findViewById(R.id.chat);
        chat.setText("");
        ImageButton sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (network.isConnected()) {
                    EditText editSend = findViewById(R.id.editSend);
                    String text=editSend.getText().toString();
                    network.sendMessage(text);
                    editSend.setText("");
                }
            }
        });
    }

}