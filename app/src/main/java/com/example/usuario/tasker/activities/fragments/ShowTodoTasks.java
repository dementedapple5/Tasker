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
import com.example.usuario.tasker.pojoObjects.TaskPojo;
import com.example.usuario.tasker.remote.ApiUtils;
import com.example.usuario.tasker.remote.SOService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowTodoTasks extends Fragment {
    public static TaskAdapter adapter;
    public static View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_show_todo_tasks, container, false);


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addTasks(v);
    }


    public static void addTasks(View v){


        ListView lv = v.findViewById(R.id.lv_tasks_todo);
        ArrayList<Task> arrayList = new ArrayList<>();
        adapter = new TaskAdapter(v.getContext(),arrayList);
        adapter.clear();
        adapter.notifyDataSetChanged();

        RequestBody usernameRB = RequestBody.create(MediaType.parse("text/plain"), TabbedTasks.username);

        SOService service = ApiUtils.getSOService();
        Call<List<TaskPojo>> req = service.select_tasks(usernameRB);

        req.enqueue(new Callback<List<TaskPojo>>() {
            @Override
            public void onResponse(Call<List<TaskPojo>> call, Response<List<TaskPojo>> response) {
                List<TaskPojo> users = response.body(); // have your all data


                for (TaskPojo taskPojo : users) {
                    Task task = new Task(taskPojo.getTitulo(),taskPojo.getEncargado(),taskPojo.getComents(),taskPojo.getContenido(),Integer.parseInt(taskPojo.getPrioridad()),taskPojo.getFecha(),taskPojo.getEstado());
                    if (!task.isState()){
                        adapter.addItem(task);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<TaskPojo>> call, Throwable t) {

            }

        });

        lv.setAdapter(adapter);

    }




}
