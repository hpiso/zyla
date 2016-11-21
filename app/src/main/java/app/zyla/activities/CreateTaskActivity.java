package app.zyla.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import app.zyla.R;
import app.zyla.database.DatabaseHandler;
import app.zyla.models.Task;

public class CreateTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);
    }

    /** Called when the user clicks the Send button */
    public void createTask(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        EditText taskNameField = (EditText) findViewById(R.id.taskDescription);
        String taskName = taskNameField.getText().toString();

        Task task = new Task();
        task.setName(taskName);
        task.setIsDone(0);

        DatabaseHandler db = new DatabaseHandler(CreateTaskActivity.this);
        db.addTask(task);

        startActivity(intent);
    }
}
