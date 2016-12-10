package app.zyla.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import app.zyla.R;
import app.zyla.activities.CreateTaskActivity;
import app.zyla.activities.ShowTaskActivity;
import app.zyla.adapters.TasksAdapter;
import app.zyla.database.DatabaseHandler;
import app.zyla.models.Task;

public class StateFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ArrayList<Task> tasks;
    private TasksAdapter adapter;

    public StateFragment() {
        // Required empty public constructor
    }


    public static StateFragment newInstance(int sectionNumber) {
        StateFragment fragment = new StateFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, container, false);
        final ListView taskListView = (ListView) rootView.findViewById(R.id.task_list);
        DatabaseHandler db = new DatabaseHandler(getContext());

        switch (getArguments().getInt(ARG_SECTION_NUMBER))
        {
            case 1: //todos-tasks

                tasks = db.getAllTodoTasks();
                break;

            case 2: //done-tasks

                tasks = db.getAllDoneTasks();
                break;

        }

        TasksAdapter adapter = new TasksAdapter(getContext(), tasks, this);
        taskListView.setAdapter(adapter);
        taskListView.setItemsCanFocus(true);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ShowTaskActivity.class);
                Task task = (Task) parent.getItemAtPosition(position);
                intent.putExtra("taskToShow", task);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
