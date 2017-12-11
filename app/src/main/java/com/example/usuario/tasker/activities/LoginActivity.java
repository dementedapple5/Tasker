package com.example.usuario.tasker.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
    private TextInputLayout passWrapper, userWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        passWrapper = findViewById(R.id.pass_wrapper);
        userWrapper = findViewById(R.id.user_wrapper);
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_signup);
        checkRemember = findViewById(R.id.check_remember);

        passWrapper.setHint("Password");
        userWrapper.setHint("Username");

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

        sendRegistrationToServer(FirebaseInstanceId.getInstance().getToken());


    }


    public static class MyFirebaseMessagingService extends FirebaseMessagingService {
        String title, text;

        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {
            // TODO: Handle FCM messages here.
            // If the application is in the foreground handle both data and notification messages here.
            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated.
            title = remoteMessage.getNotification().getTitle();
            text = remoteMessage.getNotification().getBody();
            android.support.v4.app.NotificationCompat.Builder mBuilder;
            NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
            int icono = R.mipmap.ic_launcher;
            Intent i = new Intent(this, LoginActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
            mBuilder = new NotificationCompat.Builder(getApplicationContext())
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(icono)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setVibrate(new long[]{100, 250, 100, 500})
                    .setAutoCancel(true);
            mNotifyMgr.notify(1, mBuilder.build());
        }
    }

    public class FirebaseIDService extends FirebaseInstanceIdService {

        private static final String TAG = "FirebaseIDService";

        @Override
        public void onTokenRefresh() {
            // Get updated InstanceID token.
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            // TODO: Implement this method to send any registration to your app's servers.
            sendRegistrationToServer(refreshedToken);
        }
    }

    public void sendRegistrationToServer(String refreshedToken){
        String usernameStr = etUsername.getText().toString().trim();
        String passwordStr = etPassword.getText().toString().trim();
        SOService service = ApiUtils.getSOService();
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), refreshedToken);
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), usernameStr);
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), passwordStr);

        Call<ResponseBody> req = service.updateToken(username,password,token);
        req.enqueue(new Callback<ResponseBody>() {


            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



        //insertar bd interna o server o ambas

    }


    /*
    Server Key
    AAAAyYHqE8k:APA91bF9mmd3oYy4mnoAgebS_mlod6Qyw6munRYLj0DGkEg5faZ8JOml0gwqMDIbc50s0hGj8Pelfo654ACZD69TFrEJ5SZea-xlW-bRU4651OoZK6JReACP8FRWnwWOm_yAGmc7p1Wz
    *
    *
    *
    *
    * */

}
