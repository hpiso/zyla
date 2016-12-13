package app.zyla.database;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.zyla.models.Task;
import app.zyla.models.Trophy;
import app.zyla.models.User;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "zyla";

    //Table Tasks
    public static final String TABLE_TASKS = "tasks";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_IS_DONE = "is_done";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_CREATION_DATE = "creation_date";
    public static final String KEY_IS_DONE_DATE = "is_done_date";
    public static final String KEY_LIMIT_DATE = "limit_date";
    public static final String KEY_LIMIT_TIME = "limit_time";
    public static final String KEY_MOTIVATION = "motivation";

    //Logged user table
    public static final String TABLE_USER = "user";
    public static final String KEY_PWD = "pwd";
    public static final String KEY_AGE = "age";
    public static final String KEY_GENDER = "gender";

    //Trophy table
    public static final String TABLE_TROPHY = "trophy";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_DATE = "date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("databse updated");
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_IS_DONE + " INTEGER DEFAULT 0,"
                + KEY_CATEGORY + " TEXT," + KEY_CREATION_DATE + " DATETIME," + KEY_IS_DONE_DATE + " DATETIME NULL,"
                + KEY_LIMIT_DATE + " DATE," + KEY_LIMIT_TIME + " TIME," + KEY_MOTIVATION + " INTEGER" + ")";
        db.execSQL(CREATE_TASKS_TABLE);

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PWD + " TEXT," + KEY_AGE + " INTEGER," + KEY_GENDER
                + " INTEGER" + ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_TROPHY_TABLE = "CREATE TABLE " + TABLE_TROPHY + "("
                + KEY_ID + "INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + KEY_DESCRIPTION
                + " TEXT," + KEY_IMAGE + " TEXT," + KEY_DATE + " DATETIME)";
        db.execSQL(CREATE_TROPHY_TABLE);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }

    // Adding new task
    public void addTask(Task task) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_NAME, task.getName());
        values.put(DatabaseHandler.KEY_IS_DONE, task.getIsDone());
        values.put(DatabaseHandler.KEY_CATEGORY, task.getCategory());
        values.put(DatabaseHandler.KEY_CREATION_DATE, task.getCreationDate());
        values.put(DatabaseHandler.KEY_LIMIT_DATE, task.getLimitDate());
        values.put(DatabaseHandler.KEY_LIMIT_TIME, task.getLimitTime());
        values.put(DatabaseHandler.KEY_MOTIVATION, task.getMotivation());

        // Inserting Row
        db.insert(DatabaseHandler.TABLE_TASKS, null, values);
        db.close(); // Closing database connection
    }


    // Update task
    public void updateTask(Task task) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_IS_DONE, task.getIsDone());
        values.put(DatabaseHandler.KEY_IS_DONE_DATE, task.getIsDoneDate());

        // Update Row
        db.update(DatabaseHandler.TABLE_TASKS, values, KEY_ID + "=" + task.getId(), null);
        db.close(); // Closing database connection
    }

    // Delete task
    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + "=" + task.getId(), null);
        db.close(); // Closing database connection
    }


    public ArrayList<Task> getAllTodoTasks() {
        ArrayList<Task> taskList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DatabaseHandler.TABLE_TASKS
                + " WHERE "+ KEY_IS_DONE +" = 0" + " ORDER BY " + KEY_LIMIT_DATE + ", "
                + KEY_LIMIT_TIME + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setName(cursor.getString(1));
                task.setIsDone(cursor.getInt(2));
                task.setCategory(cursor.getString(3));
                task.setCreationDate(cursor.getString(4));
                task.setLimitDate(cursor.getString(6));
                task.setLimitTime(cursor.getString(7));
                task.setMotivation(cursor.getInt(8));
                // Adding contact to list
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        // return contact list
        return taskList;
    }

    public ArrayList<Task> getAllDoneTasks() {
        ArrayList<Task> taskList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DatabaseHandler.TABLE_TASKS
                + " WHERE "+ KEY_IS_DONE + " = 1" + " ORDER BY " + KEY_LIMIT_DATE + ", "
                + KEY_LIMIT_TIME + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setName(cursor.getString(1));
                task.setIsDone(cursor.getInt(2));
                task.setCategory(cursor.getString(3));
                task.setCreationDate(cursor.getString(4));
                task.setLimitDate(cursor.getString(6));
                task.setLimitTime(cursor.getString(7));
                task.setMotivation(cursor.getInt(8));
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        // return contact list
        return taskList;
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_NAME, user.getName());
        values.put(DatabaseHandler.KEY_PWD, user.getPwd());
        values.put(DatabaseHandler.KEY_AGE, user.getAge());
        values.put(DatabaseHandler.KEY_GENDER, user.getGender());

        // Inserting Row
        db.insert(DatabaseHandler.TABLE_USER, null, values);
        db.close(); // Closing database connection
    }

    public User getUser() {
        String query = "SELECT * FROM " + DatabaseHandler.TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst())
            return new User(cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt((4)));
        else
            return null;
    }

    public void dltUser() {
        String query = "DELETE FROM " + DatabaseHandler.TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void addTrophy(Trophy trophy) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_TITLE, trophy.getTitle());
        values.put(DatabaseHandler.KEY_DESCRIPTION, trophy.getDescription());
        values.put(DatabaseHandler.KEY_IMAGE, trophy.getImg());
        values.put(DatabaseHandler.KEY_DATE, trophy.getDate());

        db.insert(DatabaseHandler.TABLE_TROPHY, null, values);
        db.close();
    }

    public ArrayList<Trophy> getTrophies() {
        ArrayList<Trophy> trophies = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHandler.TABLE_TROPHY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Trophy trophy = new Trophy(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4));
                trophies.add(trophy);
            } while (cursor.moveToNext());
        }
        return trophies;
    }
}