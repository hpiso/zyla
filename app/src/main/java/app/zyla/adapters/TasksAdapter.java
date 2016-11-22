package app.zyla.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import app.zyla.R;
import app.zyla.models.Task;

public class TasksAdapter extends ArrayAdapter<Task> {
    public TasksAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        TextView taskName = (TextView) convertView.findViewById(R.id.name);
        taskName.setText(task.getName());

        TextView isDone = (TextView) convertView.findViewById(R.id.isDone);

        if (task.getIsDone() == 0) {
            isDone.setText("Todo");
            isDone.setTextColor(Color.parseColor("#FFFF4043"));
        } else {
            isDone.setText("Done");
            isDone.setTextColor(Color.parseColor("#FF5B8E2C"));
        }

        return convertView;
    }

}
