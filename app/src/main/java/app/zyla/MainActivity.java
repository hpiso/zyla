package app.zyla;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {

    private ListView taskListView;
    private String url = "https://jsonplaceholder.typicode.com/todos"; //todo change to real webservice (just testing)

    ArrayList<HashMap<String, String>> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskList = new ArrayList<>();
        taskListView = (ListView) findViewById(R.id.task_list);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ShowTaskActivity.class);
                Object itemAtPosition = taskListView.getItemAtPosition(position).hashCode();
                //itemAtPosition.get()
                //intent.putExtra("test", itemAtPosition);
                System.out.println(itemAtPosition);
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
                    System.out.println(tasks);

                    for (int i = 0; i < tasks.length(); i++) {
                        JSONObject t = tasks.getJSONObject(i);
                        String id    = t.getString("id");
                        String title = t.getString("title");

                        HashMap<String, String> task = new HashMap<>();
                        task.put("id", id);
                        task.put("title", title);

                        // adding task to task list
                        taskList.add(task);
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

            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,
                    taskList,
                    R.layout.list_item,
                    new String[] {"title", "id"},
                    new int[]{R.id.title}
            );

            taskListView.setAdapter(adapter);
        }

    }

}
