package com.example.usuario.tasker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.usuario.tasker.R;

public class EditTaskActivity extends AppCompatActivity {
    private EditText etTaskTitle, etTaskComment, etTaskDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Bundle bundleR = getIntent().getExtras();
        etTaskTitle = findViewById(R.id.et_task_name);
        etTaskComment = findViewById(R.id.et_task_comment);
        etTaskDesc = findViewById(R.id.et_task_desc);

        etTaskTitle.setText(bundleR.getString("TASK_TITLE"));
        etTaskComment.setText(bundleR.getString("TASK_COMMENT"));
        etTaskDesc.setText(bundleR.getString("TASK_DESC"));
    }
}
