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


public class ShowTodoTasks extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setup(TabbedTasks.tasksTODO);

        return inflater.inflate(R.layout.fragment_show_todo_tasks, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void setup(ArrayList<Task> tasks) {

        ListView lv = getView().findViewById(R.id.lv_tasks_todo);
        TaskAdapter adapter = new TaskAdapter(getActivity(), tasks);
        lv.setAdapter(adapter);

    }


}
