package com.example.usuario.tasker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.tasker.R;
import com.example.usuario.tasker.database.ConnectionSQLiteHelper;
import com.example.usuario.tasker.remote.ApiUtils;
import com.example.usuario.tasker.remote.SOService;
import com.example.usuario.tasker.utilities.Utilities;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        SOService service = ApiUtils.getSOService();
        Call<ResponseBody> req = service.userExists(username,password);

        req.enqueue(new Callback<ResponseBody>() {
            String result;

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("USEREXISTS Exito::", "" + response.body().string());
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(LoginActivity.this, result,Toast.LENGTH_LONG).show();
                if(result.equals("1")){
                    Intent intent = new Intent(LoginActivity.this,TasksActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), Utilities.USERNAME_PASSWORD_INCORRECT,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("USEREXISTS Fail::", t.getMessage());
            }
        });

    }


}
