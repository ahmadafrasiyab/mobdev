package com.example.mobdev;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Provides UI for the view with List.
 */
public class ProfileFragment extends Fragment {
    private String name = "";
    private String email = "";
    private String age = "";
    private String description = "";
    private String occupation = "";
    private String dateOfBirth = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profile_fragment, null);

        TextView textViewName = root.findViewById(R.id.textViewName);
        TextView textViewAge = root.findViewById(R.id.textViewAge);
        TextView textViewEmail = root.findViewById(R.id.textViewEmail);
        TextView textViewDescription = root.findViewById(R.id.textViewDescription);
        TextView textViewOccupation = root.findViewById(R.id.textViewOccupation);
        TextView textViewDateOfBirth = root.findViewById(R.id.textViewDateOfBirth);


        Bundle b = getArguments();

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
            age = "Age: " + age;
            email = "Email: " + email;
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
        return root;
    }
}

