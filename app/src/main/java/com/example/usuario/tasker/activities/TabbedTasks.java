package com.example.usuario.tasker.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.example.usuario.tasker.R;
import com.example.usuario.tasker.activities.fragments.ShowDoneTasks;
import com.example.usuario.tasker.activities.fragments.ShowTodoTasks;
import com.example.usuario.tasker.objects.Task;
import com.example.usuario.tasker.utilities.SectionsPagerAdapter;

import java.util.ArrayList;

public class TabbedTasks extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton addTaskFAB;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static ArrayList<Task> tasks = new ArrayList<>();

    private TabLayout tabs;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_tasks);

        addTasks(tasks);
        mViewPager = findViewById(R.id.container);

        setupViewPager(mViewPager);

        addTaskFAB = findViewById(R.id.btn_add_task);
        addTaskFAB.setOnClickListener(this);

        tabs = findViewById(R.id.tabs);

        tabs.setupWithViewPager(mViewPager);
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));

    }

    private void setupViewPager(ViewPager vp){
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new ShowDoneTasks(),"done");
        mSectionsPagerAdapter.addFragment(new ShowTodoTasks(),"todo");
        vp.setAdapter(mSectionsPagerAdapter);
    }

    private void addTasks(ArrayList<Task> tasks){
        tasks.add(new Task("Reparar base de datos","pepin","rapitdo","Hay que arreglar el estropicio de ayer",1));
        tasks.add(new Task("Hacer deberes","dani","rapitdo","Hay que arreglar el estropicio de ayer",2));
        tasks.add(new Task("Tirar la basura","sac","rapitdo","Hay que arreglar el estropicio de ayer",3));
    }


    @Override
    public void onClick(View view) {
        if (view==addTaskFAB){
            Intent intent = new Intent(this,AddTaskActivity.class);
            startActivity(intent);
        }
    }
}
