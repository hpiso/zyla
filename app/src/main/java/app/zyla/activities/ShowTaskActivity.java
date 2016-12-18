package app.zyla.activities;

import android.app.LauncherActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import app.zyla.R;
import app.zyla.Service.Helper;
import app.zyla.database.DatabaseHandler;
import app.zyla.listener.isDoneEventListener;
import app.zyla.models.Task;

public class ShowTaskActivity extends AppCompatActivity {

    private Drawable bg;
    private ShowTaskActivity showTaskActivity = this;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Task task = (Task) getIntent().getSerializableExtra("taskToShow");
        setContentView(R.layout.show_task);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_bg);

        switch (task.getCategory()) {
            case "Sport":
                bg = getResources().getDrawable(R.drawable.bg_sport);
                break;
            case "Study":
                bg = getResources().getDrawable(R.drawable.bg_study);
                break;
            case "Cleaning":
                bg = getResources().getDrawable(R.drawable.bg_cleaning);
                break;
            case "Grocery":
                bg = getResources().getDrawable(R.drawable.bg_grocery);
                break;
        }

        layout.setBackground(bg);

        TextView taskToShow = (TextView) findViewById(R.id.name);
        taskToShow.setText(task.getName());

        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setMax(100);

        Helper helper = new Helper();
        int evolutionInPercentage = helper.getEvolutionInPercentage(task);

        if (evolutionInPercentage <= 40) {
            pb.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        } else if (evolutionInPercentage >= 70) {
            pb.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        }

        TextView taskEvolution = (TextView) findViewById(R.id.task_evolution);
        taskEvolution.setText(evolutionInPercentage + " %");
        pb.setProgress(evolutionInPercentage);

        Button adviseBtn = (Button) findViewById(R.id.advise_btn);

        switch (task.getCategory()) {
            case "Cleaning":
                adviseBtn.setVisibility(View.GONE);
                break;
            case "Study":
                adviseBtn.setVisibility(View.GONE);
                break;
        }

        adviseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch (task.getCategory()) {
                    case "Sport":
                        intent = new Intent(showTaskActivity, SportAdivseActivity.class);
                        break;
                    case "Grocery":
                        intent = new Intent(showTaskActivity, MapsActivity.class);
                        break;
                }

                showTaskActivity.startActivity(intent);
            }
        });


        Button deleteBtn = (Button) findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(showTaskActivity);
                db.deleteTask(task);

                intent = new Intent(showTaskActivity, MainActivity.class);
                showTaskActivity.startActivity(intent);

                Toast toast = Toast.makeText(showTaskActivity, "Task deleted", Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }

}
