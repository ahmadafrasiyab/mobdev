package com.example.mobdev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    private String name ="";
    private String email ="";
    private String age = "";
    private String description ="";
    private String occupation ="";
    private String dateOfBirth ="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewAge =  findViewById(R.id.textViewAge);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        TextView textViewOccupation =  findViewById(R.id.textViewOccupation);
        TextView textViewDateOfBirth = findViewById(R.id.textViewDateOfBirth);

        //StringBuilder msg = new StringBuilder("Thank you ");
        Intent intent = getIntent();
        Bundle b = intent.getExtras();


        if (b != null) {
            if (b.containsKey(Constants.KEY_NAME)) {
                name = b.getString(Constants.KEY_NAME);
            }

            if (b.containsKey(Constants.KEY_EMAIL)) {
                email = b.getString(Constants.KEY_EMAIL);
            }

            if (b.containsKey(Constants.KEY_AGE)) {
                age = b.getString(Constants.KEY_AGE);
            }

            if (b.containsKey(Constants.KEY_DESCRIPTION)) {
                description = b.getString(Constants.KEY_DESCRIPTION);
            }

            if (b.containsKey(Constants.KEY_OCCUPATION)) {
                occupation = b.getString(Constants.KEY_OCCUPATION);
            }

            if (b.containsKey(Constants.KEY_DATE_OF_BIRTH)) {
                dateOfBirth = b.getString(Constants.KEY_DATE_OF_BIRTH);
            }
            name = "Name: " + name;
            age  =  "Age: " + age;
            email ="Email: " + email;
            description = "Description: " + description;
            occupation = "Occupation: " + occupation;
            dateOfBirth = "Date Of Birth: " + dateOfBirth;

            textViewName.setText(name);
            textViewAge.setText(age);
            textViewEmail.setText(email);
            textViewDescription.setText(description);
            textViewDateOfBirth.setText(dateOfBirth);
            textViewOccupation.setText(occupation);
        }
    }

    public void goToFirstActivity(View view) {
        finish();
    }
}

