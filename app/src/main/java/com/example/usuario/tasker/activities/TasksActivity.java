package com.example.usuario.tasker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.usuario.tasker.R;
import com.example.usuario.tasker.adapter.TaskAdapter;
import com.example.usuario.tasker.objects.Task;
import com.example.usuario.tasker.objects.User;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity {
    Button btnAddTask;
    private ListView mTaskListView;
    ArrayList<Task> tasks = new ArrayList<>();
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        username = (String) getIntent().getExtras().get("USERNAME");
        setup();
    }

    private void setup() {

        tasks.add(new Task("Reparar base de datos","pepin","rapitdo","Hay que arreglar el estropicio de ayer",1));
        tasks.add(new Task("Hacer deberes","dani","rapitdo","Hay que arreglar el estropicio de ayer",2));
        tasks.add(new Task("Tirar la basura","sac","rapitdo","Hay que arreglar el estropicio de ayer",3));

        btnAddTask = (Button) findViewById(R.id.btn_add_task);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TasksActivity.this,AddTaskActivity.class);
                intent.putExtra("USERNAME",username);
                TasksActivity.this.startActivity(intent);
            }
        });
        ListView lv = findViewById(R.id.lv_tasks);
        TaskAdapter adapter = new TaskAdapter(this, tasks);
        lv.setAdapter(adapter);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(TaskAdapter.REQUEST_CODE==requestCode && resultCode==RESULT_OK){
//
//        }
//    }
}



