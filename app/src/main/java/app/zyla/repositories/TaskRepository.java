package app.zyla.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import app.zyla.database.DatabaseHandler;
import app.zyla.models.Task;

/**
 * Created by hugopiso on 02/11/2016.
 */
public class TaskRepository extends SQLiteOpenHelper {

    public TaskRepository(Context context) {
        super(context, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Adding new task
    public void addTask(Task task) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_NAME, task.getName());

        // Inserting Row
        db.insert(DatabaseHandler.TABLE_TASKS, null, values);
        db.close(); // Closing database connection
    }

    // Getting All tasks
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setName(cursor.getString(1));
                // Adding contact to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        // return contact list
        return taskList;
    }
}
