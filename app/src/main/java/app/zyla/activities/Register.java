package app.zyla.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import app.zyla.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import app.zyla.R;
import app.zyla.database.DatabaseHandler;
import app.zyla.login.Inscription;
import app.zyla.models.User;

/**
 * Created by trist_000 on 08/12/2016.
 */

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }

    public void Inscription(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        EditText emailField = (EditText) findViewById(R.id.register_email);
        String email = emailField.getText().toString();
        //Check if password == confpassword
        EditText passwordField = (EditText) findViewById(R.id.register_password);
        //hash function for the password
        String password = passwordField.getText().toString();

        EditText ageField = (EditText) findViewById(R.id.register_age);
        int age = Integer.valueOf(ageField.getText().toString());
        RadioGroup genderRadio = (RadioGroup) findViewById(R.id.register_gender);
        RadioButton genderSelected = (RadioButton) findViewById(genderRadio.getCheckedRadioButtonId());
        int gender;
        if (genderSelected.getText().equals(R.string.reg_gender_man))
            gender = 0;
        else
            gender = 1;

        Inscription inscription = new Inscription(email, password, "manuel");
        try {
            String msg = inscription.execute("").get();
            if (msg.equals("Success")) {
                DatabaseHandler dbhandler = new DatabaseHandler(Register.this);
                dbhandler.addUser(new User(email, password, age, gender));

                startActivity(intent);
            }
        } catch (Exception e) {
            System.err.println("Error during inscription: " + e.toString());
        }
    }
}
