package ru.wtw.moreliatalkclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            Button btnLogin = findViewById(R.id.btnLogin);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editServername = findViewById(R.id.editServername);
                    EditText editUsername = findViewById(R.id.editUsername);
                    EditText editPassword = findViewById(R.id.editPassword);
                    String username = editUsername.getText().toString();
                    String password = editPassword.getText().toString();
                    String servername = editServername.getText().toString();
                    if (servername.isEmpty() || username.isEmpty() || password.isEmpty()) {
//                        TextView auth_hint = findViewById(R.id.auth_hint);
//                        auth_hint.setText("ALL FIELDS MUST BE FILLED");
                    } else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);
                        intent.putExtra("servername", servername);
                        startActivity(intent);
                    }
                }
            });
        }
}
