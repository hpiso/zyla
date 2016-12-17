package app.zyla.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

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
        ArrayList<Integer>  scores = db.getScore();
        Integer nbTaskTodo = tasksTodos.size();
        Integer nbTaskDone = tasksDones.size();

        int sum = 0;
        for (int d : scores) sum += d;
        int average = sum / scores.size();

        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setMax(100);

        TextView taskEvolution = (TextView) findViewById(R.id.level_procrastination);
        taskEvolution.setText(average + " %");
        pb.setProgress(average);

    }
}
