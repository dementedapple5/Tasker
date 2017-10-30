package com.example.usuario.tasker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity {
    EditText etTitle, etComment, etDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
    }
}
