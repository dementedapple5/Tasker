package com.example.usuario.tasker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usuario.tasker.database.ConnectionSQLiteHelper;
import com.example.usuario.tasker.utilities.Utilities;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etUsername, etName, etPassword, etRpassword;
    private Button btnAddUser;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = (EditText) findViewById(R.id.et_signup_username);
        etName = (EditText) findViewById(R.id.et_signup_name);
        etPassword = (EditText) findViewById(R.id.et_signup_password);
        etRpassword = (EditText) findViewById(R.id.et_signup_rpassword);

        btnAddUser = (Button) findViewById(R.id.btn_signup);

        btnAddUser.setOnClickListener(this);
    }

    public void createUser(){
        ConnectionSQLiteHelper conn = new ConnectionSQLiteHelper(this, Utilities.DATABASE_NAME,null,Utilities.DATABASE_VERSION);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilities.USER_USERNAME,etUsername.getText().toString());
        values.put(Utilities.USER_NAME,etName.getText().toString());
        values.put(Utilities.USER_PASSWORD,etPassword.getText().toString());
        db.insert(Utilities.USER_TABLE,null,values);
        Toast.makeText(getApplicationContext(),"usuario añadido correctamente",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {
        createUser();
    }
}
