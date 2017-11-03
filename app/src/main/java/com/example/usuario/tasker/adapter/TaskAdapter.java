package com.example.usuario.tasker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.usuario.tasker.R;
import com.example.usuario.tasker.activities.EditTaskActivity;
import com.example.usuario.tasker.objects.Task;

import java.util.ArrayList;

/**
 * Created by Samuel on 31/10/2017.
 */

public class TaskAdapter extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Task> items;
    private ImageView delete;
    private ImageView edit;
    private CheckBox checked;


    public TaskAdapter(Activity activity, ArrayList<Task> items) {
        this.activity = activity;
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


        View v = view;

        if (v == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.task_item_template, null);
        }

        delete = (ImageView) v.findViewById(R.id.item_delete);
        edit = (ImageView) v.findViewById(R.id.item_edit);
        checked = (CheckBox) v.findViewById(R.id.item_done);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Has eliminado ésta tarea", Toast.LENGTH_LONG).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditTaskActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Has completado ésta tarea", Toast.LENGTH_LONG).show();
            }
        });

        Task dir = items.get(position);



        int priority = dir.getPriority();
        if(priority == 1){
            view.setBackgroundResource(R.color.major_prior_task);

        }
        if (priority == 2){
            view.setBackgroundResource(R.color.medium_prior_task);
        }

        if (priority == 3){
            view.setBackgroundResource(R.color.minor_prior_task);
        }


        TextView title = v.findViewById(R.id.item_title);
        title.setText(dir.getTitle());

        TextView description = v.findViewById(R.id.item_description);
        description.setText(dir.getDescription());

        TextView date = v.findViewById(R.id.item_date);
        date.setText(dir.getCreationDate());

        return v;

    }
}
