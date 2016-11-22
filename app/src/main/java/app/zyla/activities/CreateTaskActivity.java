package app.zyla.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import app.zyla.R;
import app.zyla.database.DatabaseHandler;
import app.zyla.models.Category;
import app.zyla.models.Task;

public class CreateTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.task_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /** Called when the user clicks the Send button */
    public void createTask(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        EditText taskNameField = (EditText) findViewById(R.id.taskDescription);
        String taskName = taskNameField.getText().toString();

        Spinner spinnerCategory = (Spinner) findViewById(R.id.spinnerCategories);
        String category = spinnerCategory.getSelectedItem().toString();

        Task task = new Task();
        task.setName(taskName);
        task.setIsDone(0);
        task.setCategory(category);

        DatabaseHandler db = new DatabaseHandler(CreateTaskActivity.this);
        db.addTask(task);

        startActivity(intent);
    }
}
