package app.zyla.activities;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import app.zyla.R;
import app.zyla.database.DatabaseHandler;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.zyla.R;
import app.zyla.database.DatabaseHandler;
import app.zyla.login.SignIn;
import app.zyla.models.User;
//import app.zyla.models.User;

/**
 * Created by trist_000 on 08/12/2016.
 */


public class Connection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHandler dbhandler = new DatabaseHandler(Connection.this);
        if (dbhandler.getUser() != null)
            startActivity(new Intent(this, MainActivity.class));
        else
            setContentView(R.layout.connection);
    }

    public void SignIn(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        EditText emailField = (EditText) findViewById(R.id.email_co);
        String email = emailField.getText().toString();
        EditText passwordField = (EditText) findViewById(R.id.password);
        //hash function for the password
        String password = passwordField.getText().toString();

        SignIn signin = new SignIn(email, password);
        try {
            String user_info = signin.execute("").get();
            if (user_info != "") {
                User user = parseInfo(email, password, user_info);
                DatabaseHandler dbhandler = new DatabaseHandler(Connection.this);
                dbhandler.addUser(user);

                Context context = getApplicationContext();
                CharSequence text = "Hello " + user.getEmail() + " !";
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
            else {
                //ERROR MSG
            }
        } catch (Exception e) {
            System.err.println("Error during Log in: " + e.toString());
        }
    }

    public void Register(View view) {
        Intent intent = new Intent(this, Register.class);

        startActivity(intent);
    }

    private User parseInfo(String email, String password, String infos) {
        String[] tab_info = infos.split("\n");
        String birthdate = tab_info[0];
        int year = Integer.valueOf(birthdate.substring(4));
        DateFormat format = new SimpleDateFormat("yyyy");
        Date date = new Date();
        int age = Integer.valueOf(format.format(date)) - year;
        int gender = Integer.valueOf(tab_info[1]);
        return new User(email, password, age, gender);
    }
}
