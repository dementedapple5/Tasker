package com.example.usuario.tasker.activities.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.usuario.tasker.R;
import com.example.usuario.tasker.activities.TabbedTasks;
import com.example.usuario.tasker.adapter.TaskAdapter;
import com.example.usuario.tasker.objects.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;


public class ShowTodoTasks extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_todo_tasks, container, false);
        setup(TabbedTasks.tasksTODO, v);
        return v;
    }


    private void setup(TreeSet<Task> tasks, View v) {
        ListView lv = v.findViewById(R.id.lv_tasks_todo);
        ArrayList<Task> tareasTodo = new ArrayList<>(tasks);
        TaskAdapter adapter = new TaskAdapter(v.getContext(), tareasTodo);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }

}
