package com.example.usuario.tasker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usuario.tasker.R;
import com.example.usuario.tasker.database.ConnectionSQLiteHelper;
import com.example.usuario.tasker.objects.User;
import com.example.usuario.tasker.utilities.Utilities;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etUsername, etName, etPassword, etRpassword;
    private Button btnAddUser;
    private ConnectionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        conn = new ConnectionSQLiteHelper(this);

        etUsername = (EditText) findViewById(R.id.et_signup_username);
        etName = (EditText) findViewById(R.id.et_signup_name);
        etPassword = (EditText) findViewById(R.id.et_signup_password);
        etRpassword = (EditText) findViewById(R.id.et_signup_rpassword);

        btnAddUser = (Button) findViewById(R.id.btn_signup);

        btnAddUser.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        this.registerUser();
    }


    public void registerUser(){
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String rPassword = etRpassword.getText().toString().trim();
        String name = etName.getText().toString().trim();

        HashMap<String,String> users = conn.retrieveUsers();
        if (users.containsKey(username)){
            Toast.makeText(this, Utilities.USERNAME_REPEATED_MSG,Toast.LENGTH_LONG).show();
            this.cleanFields();
        }else{
            if (rPassword.equals(password)){
                conn.createUser(username,password,name);
                this.cleanFields();
                Toast.makeText(this, Utilities.SUCCESSFULL_USER_REGISTRATION,Toast.LENGTH_LONG).show();
                finish();
            }else{
                Toast.makeText(this, Utilities.PASSWORD_DONT_MATCH,Toast.LENGTH_LONG).show();
            }
        }
    }



    public void cleanFields(){
        etName.setText("");
        etUsername.setText("");
        etPassword.setText("");
        etRpassword.setText("");
    }
}
