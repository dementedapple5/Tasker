package com.example.usuario.tasker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.tasker.R;
import com.example.usuario.tasker.database.ConnectionSQLiteHelper;
import com.example.usuario.tasker.utilities.Utilities;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etUsername, etPassword;
    private Button btnLogin, btnSignUp;
    private ConnectionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        conn = new ConnectionSQLiteHelper(this);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnSignUp = (Button) findViewById(R.id.btn_signup);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==btnLogin){
            this.login();
        }
        else if (v==btnSignUp){
            Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(intent);
        }
    }


    public void login(){
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        HashMap<String, String> users = conn.retrieveUsers();
        if (users.containsKey(username)){
            if (users.get(username).equals(password)){
                Intent intent = new Intent(LoginActivity.this,TasksActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), Utilities.USERNAME_PASSWORD_INCORRECT,Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), Utilities.USERNAME_PASSWORD_INCORRECT,Toast.LENGTH_LONG).show();
        }
    }


}
