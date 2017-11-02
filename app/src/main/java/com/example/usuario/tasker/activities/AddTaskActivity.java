package com.example.usuario.tasker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.usuario.tasker.R;

public class AddTaskActivity extends AppCompatActivity {
    EditText etTitle, etComment, etDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
    }
}
