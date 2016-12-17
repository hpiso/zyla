package app.zyla.listener;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.CompoundButton;
import android.widget.Toast;

import app.zyla.R;
import app.zyla.Service.Helper;
import app.zyla.activities.MainActivity;
import app.zyla.activities.ShowTaskActivity;
import app.zyla.adapters.SectionsPagerAdapter;
import app.zyla.adapters.TasksAdapter;
import app.zyla.database.DatabaseHandler;
import app.zyla.fragments.StateFragment;
import app.zyla.models.Task;

/**
 * Created by hugopiso on 20/11/2016.
 */
public class isDoneEventListener implements CompoundButton.OnCheckedChangeListener {

    private Task task;
    private Context context;
    private StateFragment stateFragment;
    private String toastMessage;


    public isDoneEventListener(Task task, Context context, StateFragment stateFragment) {
        this.task = task;
        this.context = context;
        this.stateFragment = stateFragment;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        DatabaseHandler db = new DatabaseHandler(this.context);

        if (isChecked) {
            task.setIsDone(1);
            toastMessage = "Task " + task.getName() + " sets as Done";

            Helper helper = new Helper();
            int evolutionInPercentage = helper.getEvolutionInPercentage(task);
            db.addScore(evolutionInPercentage);

        } else {
            task.setIsDone(0);
            toastMessage = "Task " + task.getName() + " sets as Todo";
        }

        task.setIsDoneDate(Task.getDateTime());
        db.updateTask(task);

        FragmentTransaction ft = stateFragment.getFragmentManager().beginTransaction();
        ft.detach(stateFragment).attach(stateFragment).commit();

        Toast toast = Toast.makeText(stateFragment.getContext(), toastMessage, Toast.LENGTH_LONG);
        toast.show();

    }


}
