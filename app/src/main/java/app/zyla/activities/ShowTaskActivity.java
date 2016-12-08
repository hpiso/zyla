package app.zyla.activities;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import app.zyla.R;
import app.zyla.listener.isDoneEventListener;
import app.zyla.models.Task;

public class ShowTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Task task = (Task) getIntent().getSerializableExtra("taskToShow");
        setContentView(R.layout.show_task);

        TextView taskToShow = (TextView) findViewById(R.id.name);
        taskToShow.setText(task.getName());

        Switch onOffSwitch = (Switch)  findViewById(R.id.isDone);

        if (task.getIsDone() == 0) {
            onOffSwitch.setChecked(false);
        } else {
            onOffSwitch.setChecked(true);
        }

//        onOffSwitch.setOnCheckedChangeListener(new isDoneEventListener(task, ShowTaskActivity.this) {
//        });

    }
}
