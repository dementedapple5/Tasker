package com.example.usuario.tasker.activities.fragments;

import android.content.Context;
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


public class ShowDoneTasks extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_done_tasks, container, false);
        setup(TabbedTasks.tasksDONE,v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void setup(HashSet<Task> tasks, View v) {
        ListView lv = v.findViewById(R.id.lv_tasks_done);
        ArrayList<Task> tareasDone = new ArrayList<>(tasks);
        TaskAdapter adapter = new TaskAdapter(v.getContext(), tareasDone);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.d("ADAPTER-DONE::",tasks.toString());
    }


}