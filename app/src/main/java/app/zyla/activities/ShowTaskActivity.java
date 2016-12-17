package app.zyla.activities;

import android.app.LauncherActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import app.zyla.R;
import app.zyla.Service.Helper;
import app.zyla.listener.isDoneEventListener;
import app.zyla.models.Task;

public class ShowTaskActivity extends AppCompatActivity {

    private Drawable bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Task task = (Task) getIntent().getSerializableExtra("taskToShow");
        setContentView(R.layout.show_task);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_bg);

        switch (task.getCategory()) {
            case "Sport":
                bg = getResources().getDrawable( R.drawable.bg_sport);
                break;
            case "Study":
                bg = getResources().getDrawable( R.drawable.bg_study);
                break;
            case "Cleaning":
                bg = getResources().getDrawable( R.drawable.bg_cleaning);
                break;
            case "Grocery":
                bg = getResources().getDrawable( R.drawable.bg_grocery);
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
    }

}
