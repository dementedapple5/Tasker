package com.example.usuario.tasker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.usuario.tasker.R;
import com.example.usuario.tasker.activities.EditTaskActivity;
import com.example.usuario.tasker.objects.Task;
import com.example.usuario.tasker.remote.ApiUtils;
import com.example.usuario.tasker.remote.SOService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Samuel on 31/10/2017.
 */

public class TaskAdapter extends BaseAdapter implements View.OnClickListener{

    protected Context context;
    protected ArrayList<Task> items;
    private ImageView edit;
    private CheckBox checked;
    public final static int REQUEST_CODE = 1;
    Task dir;


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
    public View getView(int position, View view, ViewGroup viewGroup) {


        LayoutInflater inf = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inf.inflate(R.layout.task_item_template, null);


        edit = (ImageView) view.findViewById(R.id.item_edit);
        checked = (CheckBox) view.findViewById(R.id.item_done);

        edit.setTag(position);

        edit.setOnClickListener(this);
        checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    completeTask();
                }
            }
        });

        dir = items.get(position);


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


        TextView title = view.findViewById(R.id.item_title);
        title.setText(dir.getTitle());

        TextView description = view.findViewById(R.id.item_description);
        description.setText(dir.getDescription());

        TextView date = view.findViewById(R.id.item_date);
        date.setText(dir.getCreationDate());

        return view;

    }

    public void completeTask(){


            SOService service = ApiUtils.getSOService();
            Call<Void> req = service.taskDone(dir.getAttendant(),dir.getTitle(),dir.getCreationDate());
            req.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(context, "Tarea completada", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Fallo al completar tarea", Toast.LENGTH_LONG).show();

                }
            });


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
            context.startActivity(intent);
        }

    }



}
