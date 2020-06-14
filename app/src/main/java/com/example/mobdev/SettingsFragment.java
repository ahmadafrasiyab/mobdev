package com.example.mobdev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SettingsFragment extends Fragment {

    public EditText dailyMatchesReminderTimeEdit;
    public EditText maximumDistanceSearchEdit;
    public EditText genderEdit;
    public EditText accountTypeEdit;
    public EditText interestedAgeRangeEdit;

    public TextView settingsIdView;
    public TextView dailyMatchesView;
    public TextView maximumDistanceSearchView;
    public TextView genderView;
    public TextView accountTypeView;
    public TextView interestedAgeRangeView;

    public Button updateSettings;
    public String email = "";

    private SettingsViewModel settingsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.settings_fragment, null);


        settingsIdView = root.findViewById(R.id.settingsIdView);
        dailyMatchesView = root.findViewById(R.id.dailyMatchesReminderTimeView);
        maximumDistanceSearchView = root.findViewById(R.id.maximumDistanceSearchView);
        genderView = root.findViewById(R.id.genderView);
        accountTypeView = root.findViewById(R.id.accountTypeView);
        interestedAgeRangeView = root.findViewById(R.id.interestedAgeRangeView);

        dailyMatchesReminderTimeEdit = root.findViewById(R.id.dailyMatchesReminderTimeEdit);
        maximumDistanceSearchEdit = root.findViewById(R.id.maximumDistanceSearchEdit);
        genderEdit = root.findViewById(R.id.genderEdit);
        accountTypeEdit = root.findViewById(R.id.accountTypeEdit);
        interestedAgeRangeEdit = root.findViewById(R.id.interestedAgeRangeEdit);

        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        // Create the observer which updates the UI.
        final Observer<List<Settings>> getSettingsObserver = newSettings -> {
            if (newSettings == null || newSettings.size() <= 0) {
                return;
            }

            Settings settings = newSettings.get(0);

            if (settings == null) {
                return;
            }

            settingsIdView.setText(settings.getSettingsId());
            dailyMatchesView.setText(settings.getDailyMatchesReminderTime());
            maximumDistanceSearchView.setText(settings.getMaximumDistanceSearch());
            genderView.setText(settings.getGender());
            accountTypeView.setText(settings.getAccountType());
            interestedAgeRangeView.setText(settings.getInterestedAgeRange());
        };


        Bundle b = getArguments();

        if (b != null) {

            if (b.containsKey(Constants.KEY_EMAIL)) {
                email = b.getString(Constants.KEY_EMAIL);
            }
        }

        String[] settingsIds = {email};
        settingsViewModel.loadAllByIds(this.getContext(), settingsIds).observe(getViewLifecycleOwner(), getSettingsObserver);

        updateSettings = root.findViewById(R.id.updateSettings);
        updateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings newSettings = new Settings();
                newSettings.setSettingsId(email);
                newSettings.setDailyMatchesReminderTime(dailyMatchesReminderTimeEdit.getText().toString());
                newSettings.setMaximumDistanceSearch(maximumDistanceSearchEdit.getText().toString());
                newSettings.setGender(genderEdit.getText().toString());
                newSettings.setAccountType(accountTypeEdit.getText().toString());
                newSettings.setInterestedAgeRange(interestedAgeRangeEdit.getText().toString());

                settingsViewModel.insertAll(v.getContext(), newSettings);

                settingsIdView.setText(newSettings.getSettingsId());
                dailyMatchesView.setText(newSettings.getDailyMatchesReminderTime());
                maximumDistanceSearchView.setText(newSettings.getMaximumDistanceSearch());
                genderView.setText(newSettings.getGender());
                accountTypeView.setText(newSettings.getAccountType());
                interestedAgeRangeView.setText(newSettings.getInterestedAgeRange());
            }
        });

        return root;
    }
}