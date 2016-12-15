package app.zyla.activities;

import android.app.LauncherActivity;
import android.content.Intent;
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
        pb.setProgress(10);

        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        Date date = new Date();
        String dateNow = dateFormat.format(date);


        try {
            Date dateCreation = dateFormat.parse(task.getCreationDate());
            Date dateToday = dateFormat.parse(dateNow);
            Date dateLimit = dateFormat.parse(task.getLimitDate() + " 00:00:00");

            int diffBetweenCreationAndToday = this.getDifferenceInDay(dateCreation, dateToday);
            int diffBetweenCreationAndLimit = this.getDifferenceInDay(dateCreation, dateLimit);
            int evolutionInPercentage = (int) ((diffBetweenCreationAndToday * 100.0f) / diffBetweenCreationAndLimit);

            TextView taskEvolution = (TextView) findViewById(R.id.task_evolution);
            taskEvolution.setText(evolutionInPercentage + " %");
            pb.setProgress(evolutionInPercentage);

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public int getDifferenceInDay(Date startDate, Date endDate){

        long different = endDate.getTime() - startDate.getTime();

        Integer differentInt = (int) (long) different;
        int secondsInMilli = 1000;
        int minutesInMilli = secondsInMilli * 60;
        int hoursInMilli = minutesInMilli * 60;
        int daysInMilli = hoursInMilli * 24;

        return differentInt / daysInMilli;
    }

}
