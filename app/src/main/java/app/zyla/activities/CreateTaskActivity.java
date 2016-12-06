package app.zyla.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.sql.Time;
import java.util.Calendar;

import app.zyla.R;
import app.zyla.database.DatabaseHandler;
import app.zyla.models.Category;
import app.zyla.models.Task;

public class CreateTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.task_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Manage Date popup (onclick and on focus change)
        EditText date = (EditText) findViewById(R.id.newDate);
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    newDate();
                }
            }

        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDate();
            }
        });


        // Manage Time popup (onclick and on focus change)
        EditText time = (EditText) findViewById(R.id.newTime);
        time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    newTime();
                }
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTime();
            }
        });
    }

    private void newDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                CreateTaskActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "DateFragment");
    }

    private void newTime() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog dpd = TimePickerDialog.newInstance(
                CreateTaskActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        dpd.show(getFragmentManager(), "TimeFragment");
    }

    /**
     * Called when the user clicks the Send button
     */
    public void createTask(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        EditText taskNameField = (EditText) findViewById(R.id.taskDescription);
        String taskName = taskNameField.getText().toString();

        Spinner spinnerCategory = (Spinner) findViewById(R.id.spinnerCategories);
        String category = spinnerCategory.getSelectedItem().toString();

        EditText limitDateField = (EditText) findViewById(R.id.newDate);
        String limitDate = limitDateField.getText().toString();

        EditText limitTimeField = (EditText) findViewById(R.id.newTime);
        String limitTime = limitTimeField.getText().toString();

        Task task = new Task();
        task.setName(taskName);
        task.setIsDone(0);
        task.setCategory(category);
        task.setLimitDate(limitDate);
        task.setLimitTime(limitTime);

        DatabaseHandler db = new DatabaseHandler(CreateTaskActivity.this);
        db.addTask(task);

        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        //String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        EditText date = (EditText) findViewById(R.id.newDate);
        date.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);

        System.out.println(date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        //String time = "You picked the following time: "+hourOfDay+"h"+minute+"m"+second;

        EditText time = (EditText) findViewById(R.id.newTime);
        time.setText(hourOfDay + ":" + minute);

        System.out.println(time);
    }
}
