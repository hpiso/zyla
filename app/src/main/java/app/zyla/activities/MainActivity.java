package app.zyla.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import app.zyla.adapters.TasksAdapter;
import app.zyla.database.DatabaseHandler;
import app.zyla.R;
import app.zyla.models.Task;
import app.zyla.repositories.TaskRepository;

public class MainActivity extends AppCompatActivity {

    private ListView taskListView;
    private ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskListView = (ListView) findViewById(R.id.task_list);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ShowTaskActivity.class);
                Task task = (Task) parent.getItemAtPosition(position);
                intent.putExtra("taskToShow", task);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                startActivity(intent);
            }
        });

        new GetTasks().execute();
    }

    /**
     * Async task class to get tasks
     */
    private class GetTasks extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {

            DatabaseHandler db = new DatabaseHandler(MainActivity.this);
            tasks = db.getAllTasks();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            TasksAdapter adapter = new TasksAdapter(MainActivity.this, tasks);
            taskListView.setAdapter(adapter);
        }
    }
}
