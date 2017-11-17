package com.example.usuario.tasker.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usuario.tasker.R;
import com.example.usuario.tasker.remote.ApiUtils;
import com.example.usuario.tasker.remote.SOService;
import com.example.usuario.tasker.utilities.Utilities;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etUsername, etPassword;
    private Button btnLogin, btnSignUp;
    private CheckBox checkRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        checkRemember = (CheckBox) findViewById(R.id.check_remember);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("NAME", "");
        String password = preferences.getString("PASSWORD", "");
        if (!name.equalsIgnoreCase("") && !password.equalsIgnoreCase("")) {
            etUsername.setText(name);
            etPassword.setText(password);
        }

        checkRemember.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btnLogin) {
            this.login();
        } else if (v == btnSignUp) {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        } else if (v == checkRemember) {
            this.saveInfo();
        }
    }

    private void saveInfo() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("NAME", username);
        editor.putString("PASSWORD", password);
        editor.apply();
    }


    public void login() {
        String usernameStr = etUsername.getText().toString().trim();
        String passwordStr = etPassword.getText().toString().trim();
        SOService service = ApiUtils.getSOService();
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), usernameStr);
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), passwordStr);
        Call<ResponseBody> req = service.userExists(username, password);
        req.enqueue(new Callback<ResponseBody>() {
            String result;

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    result = response.body().string();
                    Log.d("USEREXISTS Exito::", "" + result);
                } catch (IOException | NullPointerException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

                if (result.equals("1")) {
                    Intent intent = new Intent(LoginActivity.this, TabbedTasks.class);
                    intent.putExtra("USERNAME", etUsername.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), Utilities.USERNAME_PASSWORD_INCORRECT, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("USEREXISTS Fail::", t.getMessage());
            }
        });

    }


}
