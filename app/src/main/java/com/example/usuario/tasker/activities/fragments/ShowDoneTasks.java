package com.example.usuario.tasker.activities.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.usuario.tasker.R;
import com.example.usuario.tasker.activities.TabbedTasks;
import com.example.usuario.tasker.adapter.TaskAdapter;
import com.example.usuario.tasker.objects.Task;

import java.util.ArrayList;


public class ShowDoneTasks extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_show_done_tasks, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setup(TabbedTasks.tasksDONE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setup(ArrayList<Task> tasks) {
        ListView lv = getView().findViewById(R.id.lv_tasks_done);
        TaskAdapter adapter = new TaskAdapter(getActivity(), tasks);
        lv.setAdapter(adapter);
    }


}