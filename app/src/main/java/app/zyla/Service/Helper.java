package app.zyla.Service;

import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import app.zyla.R;
import app.zyla.models.Task;

/**
 * Created by hugopiso on 17/12/2016.
 */

public class Helper {

    private int evolutionInPercentage;

    public  int getEvolutionInPercentage(Task task) {


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
            evolutionInPercentage = (int) ((diffBetweenCreationAndToday * 100.0f) / diffBetweenCreationAndLimit);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return evolutionInPercentage;
    }

    private int getDifferenceInDay(Date startDate, Date endDate){

        long different = endDate.getTime() - startDate.getTime();

        Integer differentInt = (int) (long) different;
        int secondsInMilli = 1000;
        int minutesInMilli = secondsInMilli * 60;
        int hoursInMilli = minutesInMilli * 60;
        int daysInMilli = hoursInMilli * 24;

        return differentInt / daysInMilli;
    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
