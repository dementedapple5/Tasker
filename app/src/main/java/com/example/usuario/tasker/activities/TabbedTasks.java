package com.example.usuario.tasker.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.usuario.tasker.R;
import com.example.usuario.tasker.activities.fragments.ShowDoneTasks;
import com.example.usuario.tasker.activities.fragments.ShowTodoTasks;
import com.example.usuario.tasker.objects.Task;
import com.example.usuario.tasker.pojoObjects.TaskPojo;
import com.example.usuario.tasker.remote.ApiUtils;
import com.example.usuario.tasker.remote.SOService;
import com.example.usuario.tasker.utilities.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabbedTasks extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton addTaskFAB;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static TreeSet<Task> tasksTODO = new TreeSet<>();
    public static TreeSet<Task> tasksDONE = new TreeSet<>();

    private TabLayout tabs;
    private ViewPager mViewPager;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_tasks);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");


        mViewPager = findViewById(R.id.container);

        addTaskFAB = findViewById(R.id.btn_add_task);
        addTaskFAB.setOnClickListener(this);

        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(mViewPager);


        tabs.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        addTasks();


    }

    @Override
    protected void onResume() {
        super.onResume();
        addTasks();
    }


    private void setupViewPager(ViewPager vp){
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new ShowTodoTasks(),"todo");
        mSectionsPagerAdapter.addFragment(new ShowDoneTasks(),"done");
        vp.setAdapter(mSectionsPagerAdapter);
    }

    private void addTasks(){
        RequestBody usernameRB = RequestBody.create(MediaType.parse("text/plain"), username);

        SOService service = ApiUtils.getSOService();
        Call<List<TaskPojo>> req = service.select_tasks(usernameRB);

        req.enqueue(new Callback<List<TaskPojo>>() {
            @Override
            public void onResponse(Call<List<TaskPojo>> call, Response<List<TaskPojo>> response) {
                List<TaskPojo> users = response.body(); // have your all data
                tasksDONE.clear();
                tasksTODO.clear();

                for (TaskPojo taskPojo : users) {
                    Task task = new Task(taskPojo.getTitulo(),taskPojo.getEncargado(),taskPojo.getComents(),taskPojo.getContenido(),Integer.parseInt(taskPojo.getPrioridad()),taskPojo.getFecha(),taskPojo.getEstado());
                    if (task.isState()==false){
                        tasksTODO.add(task);
                    }else{
                        tasksDONE.add(task);
                    }
                }
                ArrayList<Task> example = new ArrayList<>(tasksTODO);
                setupViewPager(mViewPager);
                Log.d("TASK-TODO:",String.valueOf(tasksTODO.size()));
                Log.d("TASK-DONE:",String.valueOf(tasksDONE.size()));
            }
            @Override
            public void onFailure(Call<List<TaskPojo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Carga de tareas fallida", Toast.LENGTH_LONG).show();

            }

        });

    }


    @Override
    public void onClick(View view) {
        if (view==addTaskFAB){

            Intent intent = new Intent(this,AddTaskActivity.class);
            startActivity(intent);
        }
    }
}
