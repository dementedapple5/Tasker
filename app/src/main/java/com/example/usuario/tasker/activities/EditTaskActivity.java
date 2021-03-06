package com.example.usuario.tasker.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.tasker.R;
import com.example.usuario.tasker.activities.fragments.ShowDoneTasks;
import com.example.usuario.tasker.activities.fragments.ShowTodoTasks;
import com.example.usuario.tasker.objects.Task;
import com.example.usuario.tasker.pojoObjects.UserPojo;
import com.example.usuario.tasker.remote.ApiUtils;
import com.example.usuario.tasker.remote.SOService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTaskActivity extends AppCompatActivity {
    private EditText etTaskTitle, etTaskComment, etTaskDesc;
    private String date, userName;
    RadioGroup radioGroup;
    RadioButton minorButton;
    Button btnAddTask;
    Spinner userSpinner;
    int priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        setup();
    }

    private void setup() {
        Bundle bundleR = getIntent().getExtras();

        String title = bundleR.getString("TASK_TITLE");
        String comment = bundleR.getString("TASK_COMMENT");
        date = bundleR.getString("TASK_DATE");
        String user = bundleR.getString("TASK_USER");
        String description = bundleR.getString("TASK_DESC");
        priority = bundleR.getInt("TASK_PRIOR");

        Task task = new Task(title, user, comment, description, priority);
        etTaskTitle = findViewById(R.id.et_task_name);
        etTaskComment = findViewById(R.id.et_task_comment);
        etTaskDesc = findViewById(R.id.et_task_desc);
        radioGroup = findViewById(R.id.rg_prior);
        minorButton = findViewById(R.id.et_rb_minor_prior);

        btnAddTask = findViewById(R.id.et_btn_add_task);
        userSpinner = findViewById(R.id.et_users_spinner);

        etTaskTitle.setText(title);
        etTaskComment.setText(comment);
        etTaskDesc.setText(description);
        userName = user;

        if (priority == 1) {
            radioGroup.check(R.id.et_rb_major_prior);

        } else if (priority == 2) {
            radioGroup.check(R.id.et_rb_medium_prior);

        } else {
            radioGroup.check(R.id.et_rb_minor_prior);
        }

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTask();
            }
        });
        //Cambia la variable prioridad en funcion del boton que el usuario presione
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.et_rb_major_prior:
                        priority = 1;
                        break;

                    case R.id.et_rb_medium_prior:
                        priority = 2;
                        break;

                    case R.id.et_rb_minor_prior:
                        priority = 3;
                        break;
                }
            }
        });

        //Relleno del Spinner con el nombre de todos los usuarios

        SOService service = ApiUtils.getSOService();
        Call<List<UserPojo>> req = service.select_users();
        req.enqueue(new Callback<List<UserPojo>>() {
            @Override
            public void onResponse(Call<List<UserPojo>> call, Response<List<UserPojo>> response) {
                List<UserPojo> users = response.body(); // have your all data
                List<String> usersLists = new ArrayList<>();
                for (UserPojo userPojo : users) {
                    String userName = userPojo.getUsername();
                    usersLists.add(userName);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, usersLists);
                int defaultPosition = adapter.getPosition(userName);
                userSpinner.setAdapter(adapter);
                userSpinner.setSelection(defaultPosition);
            }

            @Override
            public void onFailure(Call<List<UserPojo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Carga de lista de usuarios fallida", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateTask() {

        Bundle bundleR = getIntent().getExtras();
        SOService service = ApiUtils.getSOService();
        RequestBody usernameRB = RequestBody.create(MediaType.parse("text/plain"), userName);
        RequestBody newusernameRB = RequestBody.create(MediaType.parse("text/plain"), userSpinner.getSelectedItem().toString().trim());
        RequestBody titleRB = RequestBody.create(MediaType.parse("text/plain"), etTaskTitle.getText().toString());
        RequestBody oldTitleRB = RequestBody.create(MediaType.parse("text/plain"), bundleR.getString("TASK_TITLE"));
        RequestBody dateRB = RequestBody.create(MediaType.parse("text/plain"), date);
        RequestBody commentsRB = RequestBody.create(MediaType.parse("text/plain"), etTaskComment.getText().toString());
        RequestBody prioritRB = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(priority));
        RequestBody descriptionRB = RequestBody.create(MediaType.parse("text/plain"), etTaskDesc.getText().toString());
        RequestBody stateRB = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(0));
        Call<Void> req = service.taskUpdate(newusernameRB, titleRB, commentsRB, prioritRB, descriptionRB, usernameRB, oldTitleRB, dateRB);

        req.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), "Task edited", Toast.LENGTH_LONG).show();

                ShowDoneTasks.adapter.clear();
                ShowTodoTasks.adapter.clear();
                ShowTodoTasks.addTasks(ShowTodoTasks.v);
                ShowDoneTasks.addTasks(ShowDoneTasks.v);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Edicion de  tarea fallida", Toast.LENGTH_LONG).show();
            }
        });


        setResult(1);
        finish();
    }

    private Boolean validarTask() {
        Boolean isGood = true;
        String titleStr = etTaskTitle.getText().toString();
        String comentStr = etTaskComment.getText().toString();
        String descriptionStr = etTaskDesc.getText().toString();
        if (titleStr.isEmpty()) {
            etTaskTitle.setError("Introduce un título");
            isGood = false;
        }

        if (comentStr.isEmpty()) {
            etTaskComment.setError("Introduce un comentario");
            isGood = false;
        }

        if (descriptionStr.isEmpty()) {
            etTaskDesc.setError("Introduce una descripción");
            isGood = false;
        }

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            minorButton.setError("Elije una prioridad");
            isGood = false;
        }


        int selectedItemOfMySpinner = userSpinner.getSelectedItemPosition();
        String actualPositionOfMySpinner = (String) userSpinner.getItemAtPosition(selectedItemOfMySpinner);

        if (actualPositionOfMySpinner.isEmpty()) {
            TextView errorText = (TextView) userSpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Escoge quien tiene que realizar la tarea!!");//changes the selected item text to this
            isGood = false;
        }

        return isGood;
    }
}
