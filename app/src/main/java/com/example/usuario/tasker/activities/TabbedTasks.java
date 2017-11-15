package com.example.usuario.tasker.activities;

import android.app.FragmentTransaction;
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

import java.util.TreeSet;



public class TabbedTasks extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton addTaskFAB;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static TreeSet<Task> tasksTODO = new TreeSet<>();
    public static TreeSet<Task> tasksDONE = new TreeSet<>();

    private TabLayout tabs;
    private ViewPager mViewPager;

    public static String username;

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
        setupViewPager(mViewPager);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void setupViewPager(ViewPager vp){
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new ShowTodoTasks(),"todo");
        mSectionsPagerAdapter.addFragment(new ShowDoneTasks(),"done");
        vp.setAdapter(mSectionsPagerAdapter);
    }


    @Override
    public void onClick(View view) {
        if (view==addTaskFAB){

            Intent intent = new Intent(this,AddTaskActivity.class);
            startActivity(intent);
        }
    }
}
