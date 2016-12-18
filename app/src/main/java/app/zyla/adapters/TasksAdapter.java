package app.zyla.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import app.zyla.R;
import app.zyla.Service.Helper;
import app.zyla.fragments.StateFragment;
import app.zyla.listener.isDoneEventListener;
import app.zyla.models.Category;
import app.zyla.models.Task;

public class TasksAdapter extends ArrayAdapter<Task> {

    private StateFragment stateFragment;

    public TasksAdapter(Context context, ArrayList<Task> tasks, StateFragment stateFragment) {
        super(context, 0, tasks);
        this.stateFragment = stateFragment;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        //set Name
        TextView taskName = (TextView) convertView.findViewById(R.id.name);
        taskName.setText(task.getName());

        // set lvl of procrastination
        Helper helper = new Helper();
        int evolutionInPercentage = helper.getEvolutionInPercentage(task);
        TextView lvlProcrastination = (TextView) convertView.findViewById(R.id.level_procrastination);
        lvlProcrastination.setText(evolutionInPercentage + "%");

        //set date
        TextView taskInfoDate = (TextView) convertView.findViewById(R.id.infoDate);
        DateFormat formatter = new SimpleDateFormat("dd MMM");
        java.sql.Timestamp ts = java.sql.Timestamp.valueOf(task.getLimitDate() + " 00:00:00");
        String dateFormated = formatter.format(ts);

        taskInfoDate.setText("Todo before "+ dateFormated + " at " + task.getLimitTime());

        LinearLayout imgLaout = (LinearLayout) convertView.findViewById(R.id.imgLayout);
        int resourceId = getContext().getResources().getIdentifier(Enum.valueOf(Category.class, task.getCategory()).toString(), "drawable",getContext().getPackageName());
        imgLaout.setBackgroundResource(resourceId);

        CheckBox isDone = (CheckBox) convertView.findViewById(R.id.isDone);

        if (task.getIsDone() == 0) {
            isDone.setChecked(false);
        } else {
            isDone.setChecked(true);
        }

        isDone.setOnCheckedChangeListener(new isDoneEventListener(task, getContext(), stateFragment) {
        });

        return convertView;
    }

}
