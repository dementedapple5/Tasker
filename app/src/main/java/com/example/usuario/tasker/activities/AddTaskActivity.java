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

public class AddTaskActivity extends AppCompatActivity {
    EditText etTitle, etComment, etDesc;
    int priority;
    RadioGroup radioGroup;
    RadioButton lastButton;
    Button btnAddTask;
    Spinner userSpinner;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        setup();
    }


    private void setup() {
        username = getIntent().getStringExtra("TASK_USER");
        etTitle = findViewById(R.id.et_task_name);
        etComment = findViewById(R.id.et_task_comment);
        etDesc = findViewById(R.id.et_task_desc);
        radioGroup = findViewById(R.id.et_radioButton);
        lastButton = findViewById(R.id.rb_minor_prior);
        btnAddTask = findViewById(R.id.btn_add_task);
        userSpinner = findViewById(R.id.users_spinner);

        //Cambia la variable prioridad en funcion del boton que el usuario presione
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_major_prior:
                        priority = 1;
                        break;
                    case R.id.rb_medium_prior:
                        priority = 2;
                        break;
                    case R.id.rb_minor_prior:
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
                int defaultPosition = adapter.getPosition(username);
                userSpinner.setAdapter(adapter);
                userSpinner.setSelection(defaultPosition);
            }

            @Override
            public void onFailure(Call<List<UserPojo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Carga de lista de usuarios fallida", Toast.LENGTH_LONG).show();
            }
        });

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
    }

    private void addTask() {

        String titleStr = etTitle.getText().toString().trim();
        String comentStr = etComment.getText().toString().trim();
        String descriptionStr = etDesc.getText().toString().trim();
        String usernameStr = userSpinner.getSelectedItem().toString().trim();

        Task task = new Task(titleStr, usernameStr, comentStr, descriptionStr, priority);

        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), task.getAttendant());
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), task.getTitle());
        RequestBody desc = RequestBody.create(MediaType.parse("text/plain"), task.getDescription());
        RequestBody comment = RequestBody.create(MediaType.parse("text/plain"), task.getComment());
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), task.getCreationDate());

        if (!validarTask()) {
            Toast.makeText(getApplicationContext(), "No se ha validado el formulario", Toast.LENGTH_LONG).show();
        } else {
            SOService service = ApiUtils.getSOService();
            Call<Void> req = service.createTask(username, title, comment, desc, task.getPriority(), date, task.isState(),task.isVisible());
            req.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    ShowDoneTasks.adapter.clear();
                    ShowTodoTasks.adapter.clear();
                    ShowTodoTasks.addTasks(ShowTodoTasks.v);
                    ShowDoneTasks.addTasks(ShowDoneTasks.v);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Fallo al insertar tarea", Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    private Boolean validarTask() {
        Boolean isGood = true;
        String titleStr = etTitle.getText().toString();
        String comentStr = etComment.getText().toString();
        String descriptionStr = etDesc.getText().toString();
        if (titleStr.isEmpty()) {
            etTitle.setError("Introduce un título");
            isGood = false;
        }

        if (comentStr.isEmpty()) {
            etComment.setError("Introduce un comentario");
            isGood = false;
        }

        if (descriptionStr.isEmpty()) {
            etDesc.setError("Introduce una descripción");
            isGood = false;
        }

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            lastButton.setError("Elije una prioridad");
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
