package app.zyla.database;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.zyla.models.Task;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "zyla";

    //Table Tasks
    public static final String TABLE_TASKS = "tasks";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_IS_DONE = "is_done";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("databse updated");
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_IS_DONE + " INTEGER DEFAULT 0" + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    /**
     * Exemple to delete and recreate database
     * DatabaseHandler db = new DatabaseHandler(MainActivity.this);
     * db.onUpgrade(db.getWritableDatabase(), 1, 2);
     * To put on onCreate main activity for example
     *
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);

        onCreate(db);
    }

    // Adding new contact
    public void addTask(Task task) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_NAME, task.getName());
        values.put(DatabaseHandler.KEY_IS_DONE, task.getIsDone());

        // Inserting Row
        db.insert(DatabaseHandler.TABLE_TASKS, null, values);
        db.close(); // Closing database connection
    }

    // Update task
    public void updateTask(Task task) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_IS_DONE, task.getIsDone());

        // Update Row
        db.update(DatabaseHandler.TABLE_TASKS, values, KEY_ID + "=" + task.getId(), null);
        db.close(); // Closing database connection
    }

    // Getting All Contacts
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DatabaseHandler.TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setName(cursor.getString(1));
                task.setIsDone(cursor.getInt(2));
                // Adding contact to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        // return contact list
        return taskList;
    }

}