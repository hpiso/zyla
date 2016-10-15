package app.zyla.activities;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import app.zyla.R;
import app.zyla.models.Task;

public class ShowTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Task task = (Task) getIntent().getSerializableExtra("taskToShow");
        setContentView(R.layout.show_task);

        TextView taskToShow = (TextView) findViewById(R.id.name);
        taskToShow.setText(task.getName());
    }
}
