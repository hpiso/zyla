package app.zyla.listener;

import android.content.Context;
import android.widget.CompoundButton;

import app.zyla.activities.ShowTaskActivity;
import app.zyla.adapters.TasksAdapter;
import app.zyla.database.DatabaseHandler;
import app.zyla.models.Task;

/**
 * Created by hugopiso on 20/11/2016.
 */
public class isDoneEventListener implements CompoundButton.OnCheckedChangeListener {

    private Task task;
    private Context context;
    private TasksAdapter adapter;

    public isDoneEventListener(Task task, Context context, TasksAdapter adapter) {
        this.task = task;
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {
            task.setIsDone(1);
        } else {
            task.setIsDone(0);
        }

        task.setIsDoneDate(Task.getDateTime());

        DatabaseHandler db = new DatabaseHandler(this.context);
        db.updateTask(task);

        adapter.clear();
        adapter.addAll(db.getAllTodoTasks());
        adapter.notifyDataSetChanged();


    }
}
