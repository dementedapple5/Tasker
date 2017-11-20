package com.example.usuario.tasker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usuario.tasker.R;
import com.example.usuario.tasker.remote.ApiUtils;
import com.example.usuario.tasker.remote.SOService;
import com.example.usuario.tasker.utilities.Utilities;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etUsername, etName, etPassword, etRpassword;
    private Button btnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etUsername = findViewById(R.id.et_signup_username);
        etName = findViewById(R.id.et_signup_name);
        etPassword = findViewById(R.id.et_signup_password);
        etRpassword = findViewById(R.id.et_signup_rpassword);
        btnAddUser = findViewById(R.id.btn_signup);
        btnAddUser.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        this.registerUser();
    }

    public void registerUser() {
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        String rPassword = etRpassword.getText().toString().trim();
        final String name = etName.getText().toString().trim();

        final String token = FirebaseInstanceId.getInstance().getToken();

        if (rPassword.equals(password)) {

            SOService service = ApiUtils.getSOService();
            Log.d("USERNAME", username);
            Log.d("PASSWORD", password);
            Call<Void> req = service.createUser(username, password, name, token);
            req.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                }
            });

            finish();


        } else {
            Toast.makeText(this, Utilities.PASSWORD_DONT_MATCH, Toast.LENGTH_LONG).show();
        }
    }
}
