package app.zyla.activities;
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

import app.zyla.R;
import app.zyla.database.DatabaseHandler;
import app.zyla.login.SignIn;
//import app.zyla.models.User;

/**
 * Created by trist_000 on 08/12/2016.
 */


public class Connection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Check if the user is already logged
        DatabaseHandler dbhandler = new DatabaseHandler(Connection.this);
        /*if (dbhandler.getUser() != null)
            startActivity(new Intent(this, MainActivity.class));
        else
            setContentView(R.layout.connection);*/
    }

    public void SignIn(View view) {
        Intent intent = new Intent(this, MainActivity.class);

       /* EditText usernameField = (EditText) findViewById(R.id.username);
        String username = usernameField.getText().toString();
        EditText passwordField = (EditText) findViewById(R.id.password);
        //hash function for the password
        String password = passwordField.getText().toString();

        //Check username and password
        //user exist in database ? -Oui-> dbhandler.addUser(user)
        //                         -Non-> Popup error, retry
        new SignIn(username, password).execute("");

        /*DatabaseHandler dbhandler = new DatabaseHandler(Connection.this);
        dbhandler.addUser(new User(username, password, 0, 0));

        startActivity(intent);*/
    }

    public void Register(View view) {
        Intent intent = new Intent(this, Register.class);

        startActivity(intent);
    }
}
