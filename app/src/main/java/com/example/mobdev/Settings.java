package com.example.mobdev;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.time.LocalDateTime;

@Entity
    public class Settings {
        @PrimaryKey
        @NonNull
        private String settingsId;

        @ColumnInfo(name = "daily_matches_reminder_time")
        private String dailyMatchesReminderTime;

        @ColumnInfo(name = "maximum_distance_search")
        private String maximumDistanceSearch;

        @ColumnInfo(name = "gender")
        private String gender;

        @ColumnInfo(name = "account_type")
        private String accountType;

        @ColumnInfo(name = "interested_age_range")
        private String interestedAgeRange;

        @NonNull
        public String getSettingsId() {
            return settingsId;
        }

        public void setSettingsId(@NonNull String settingsId) {
            this.settingsId = settingsId;
        }

        public String getDailyMatchesReminderTime() {
            return dailyMatchesReminderTime;
        }

        public void setDailyMatchesReminderTime(String dailyMatchesReminderTime) {
            this.dailyMatchesReminderTime = dailyMatchesReminderTime;
        }

        public String getMaximumDistanceSearch() {
            return maximumDistanceSearch;
        }

        public void setMaximumDistanceSearch(String maximumDistanceSearch) {
            this.maximumDistanceSearch = maximumDistanceSearch;
        }

        public String getGender() {
            return gender;
        }
        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAccountType() {
            return accountType;
        }
        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getInterestedAgeRange() {
            return interestedAgeRange;
        }
        public void setInterestedAgeRange(String interestedAgeRange) {
            this.interestedAgeRange = interestedAgeRange;
        }

}

