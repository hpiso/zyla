package app.zyla.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import app.zyla.R;
import app.zyla.database.DatabaseHandler;
import app.zyla.models.Task;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<Task> tasksTodos = db.getAllTodoTasks();
        ArrayList<Task> tasksDones = db.getAllDoneTasks();
        Integer nbTaskTodo = tasksTodos.size();
        Integer nbTaskDone = tasksDones.size();

        System.out.println(nbTaskTodo);
        System.out.println(nbTaskDone);

    }
}
