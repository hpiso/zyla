package app.zyla.adapters;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import app.zyla.R;
import app.zyla.models.Category;
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

        TextView taskInfoDate = (TextView) convertView.findViewById(R.id.infoDate);
        DateFormat formatter = new SimpleDateFormat("dd MMM");
        java.sql.Timestamp ts = java.sql.Timestamp.valueOf(task.getLimitDate() + " 00:00:00");
        String dateFormated = formatter.format(ts);

        taskInfoDate.setText("Todo before "+ dateFormated + " at " + task.getLimitTime());

        LinearLayout imgLaout = (LinearLayout) convertView.findViewById(R.id.imgLayout);
        int resourceId = getContext().getResources().getIdentifier(Enum.valueOf(Category.class, task.getCategory()).toString(), "drawable",getContext().getPackageName());
        imgLaout.setBackgroundResource(resourceId);

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
