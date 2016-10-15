package app.zyla.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import app.zyla.adapters.TasksAdapter;
import app.zyla.models.Task;
import app.zyla.sync.HttpHandler;
import app.zyla.R;

public class MainActivity extends AppCompatActivity {

    private ListView taskListView;
    private String url = "https://jsonplaceholder.typicode.com/todos"; //todo change to real webservice (just testing)

    ArrayList<Task> tasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasksList = new ArrayList<>();
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
        new GetTasks().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetTasks extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.webServiceGetCall(url);

            if (jsonStr != null) {
                try {
                    JSONArray tasks = new JSONArray(jsonStr);

                    for (int i = 0; i < tasks.length(); i++) {
                        JSONObject t = tasks.getJSONObject(i);
                        Integer id   = Integer.parseInt(t.getString("id"));
                        String title = t.getString("title");

                        Task task = new Task();
                        task.setId(id);
                        task.setName(title);

                        tasksList.add(task);
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                        }
                    });
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            TasksAdapter adapter = new TasksAdapter(MainActivity.this, tasksList);
            taskListView.setAdapter(adapter);
        }
    }

}
