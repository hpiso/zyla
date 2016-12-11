package app.zyla.listener;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import app.zyla.R;
import app.zyla.activities.AdviseActivity;
import app.zyla.database.DatabaseHandler;
import app.zyla.fragments.StateFragment;
import app.zyla.models.Task;

/**
 * Created by hugopiso on 11/12/2016.
 */
public class DeleteLongClickEventListener implements AdapterView.OnItemLongClickListener {

    private StateFragment stateFragment;

    public DeleteLongClickEventListener(StateFragment stateFragment) {
        this.stateFragment = stateFragment;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        final Task task = (Task) parent.getItemAtPosition(position);

        final Dialog dialog = new Dialog(stateFragment.getActivity());
        dialog.setTitle("Choose an action");
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);

        Button advise = (Button) dialog.findViewById(R.id.advise);
        advise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stateFragment.getContext(), AdviseActivity.class);
                stateFragment.startActivity(intent);
            }
        });

        Button delete = (Button) dialog.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(stateFragment.getContext());
                db.deleteTask(task);

                FragmentTransaction ft = stateFragment.getFragmentManager().beginTransaction();
                ft.detach(stateFragment).attach(stateFragment).commit();

                dialog.dismiss();

                Toast toast = Toast.makeText(stateFragment.getContext(), "Task deleted", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        dialog.show();

        return true;
    }
}
