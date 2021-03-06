package com.example.usuario.tasker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.usuario.tasker.R;
import com.example.usuario.tasker.activities.EditTaskActivity;
import com.example.usuario.tasker.activities.fragments.ShowDoneTasks;
import com.example.usuario.tasker.activities.fragments.ShowTodoTasks;
import com.example.usuario.tasker.objects.Task;
import com.example.usuario.tasker.remote.ApiUtils;
import com.example.usuario.tasker.remote.SOService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Samuel on 31/10/2017.
 */

public class TaskAdapter extends BaseAdapter implements View.OnClickListener{


    private static final int REQUEST_FOR_ACTIVITY_CODE = 1;
    protected Context context;
    protected ArrayList<Task> items;
    private ImageView edit, checked;
    Task dir;
    private TextView title, comment, description, date;

    public TaskAdapter(Context context, ArrayList<Task> items) {
        this.context = context;
        this.items = items;
    }




    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {


        LayoutInflater inf = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inf.inflate(R.layout.task_item_template, null);



        edit = view.findViewById(R.id.item_edit);
        checked = view.findViewById(R.id.item_done);

        edit.setTag(position);

        edit.setOnClickListener(this);
        final View finalView = view;
        dir = items.get(position);

        checked.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                completeTask(position, finalView);
            }
        });





        int priority = dir.getPriority();
        if(priority == 1){
            view.setBackgroundResource(R.drawable.major_prior_task_border);
        }
        if (priority == 2){
            view.setBackgroundResource(R.drawable.medium_prior_task_border);
        }

        if (priority == 3){
            view.setBackgroundResource(R.drawable.minor_prior_task_border);
        }

        if (dir.isState()){
            checked.setImageResource(R.drawable.ic_delete_black_24dp);
            checked.setBackgroundColor(Color.parseColor("#B71C1C"));
        }


        title = view.findViewById(R.id.item_title);
        title.setText(dir.getTitle());

        comment = view.findViewById(R.id.tv_comment);
        comment.setText(dir.getComment());

        description = view.findViewById(R.id.item_description);
        description.setText(dir.getDescription());

        date = view.findViewById(R.id.item_date);
        date.setText(dir.getCreationDate());

        return view;

    }

    public void completeTask(int position, final View view){
        dir = items.get(position);

        RequestBody attendant = RequestBody.create(MediaType.parse("text/plain"), dir.getAttendant());
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), dir.getTitle());
        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), dir.getCreationDate());


        SOService service = ApiUtils.getSOService();
        final Call<Void> req = service.taskDone(attendant,title,date);
        req.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                ShowDoneTasks.adapter.clear();
                ShowTodoTasks.adapter.clear();
                ShowTodoTasks.addTasks(ShowTodoTasks.v);
                ShowDoneTasks.addTasks(ShowDoneTasks.v);

                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Fallo al completar tarea", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void addItem(Task task){

        if(!items.contains(task)){
            items.add(task);
            this.notifyDataSetChanged();
        }

        Collections.sort(items);

    }

    public void removeItem(Task task) {
        items.remove(task);
        this.notifyDataSetChanged();
        Collections.sort(items);
    }

    public void update(ArrayList<Task> tasks){
        items.clear();
        items.addAll(tasks);
        this.notifyDataSetChanged();
        Collections.sort(items);
    }

    public void clear(){
        items.clear();
    }




    @Override
    public void onClick(View view) {

        if (view.getId()==edit.getId()){
            Intent intent = new Intent(view.getContext(), EditTaskActivity.class);
            Bundle bundle = new Bundle();
            int position = (Integer)view.getTag();
            bundle.putString("TASK_TITLE",items.get(position).getTitle());
            bundle.putString("TASK_COMMENT",items.get(position).getComment());
            bundle.putString("TASK_DESC",items.get(position).getDescription());
            bundle.putString("TASK_DATE",items.get(position).getCreationDate());
            bundle.putInt("TASK_PRIOR",items.get(position).getPriority());
            bundle.putString("TASK_USER",items.get(position).getAttendant());
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity) context).startActivityForResult(intent,REQUEST_FOR_ACTIVITY_CODE);

        }
    }



}
