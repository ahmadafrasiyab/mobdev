package com.example.mobdev;
import androidx.room.Database;
import androidx.room.RoomDatabase;

    @Database(entities = {Settings.class}, version = 3)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract SettingsDao settingsDao();
    }

