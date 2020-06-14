package com.example.mobdev;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SettingsViewModel extends ViewModel {

    public LiveData<List<Settings>> loadAllByIds(Context context, String[] settingsIds) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        return db.settingsDao().loadAllByIds(settingsIds);
    }

    public void updateSettings(Context context, Settings... settings) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        db.getTransactionExecutor().execute(() -> {
            db.settingsDao().updateSettings(settings);
        });
    }

    public void deleteSettings(Context context, Settings settings) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        db.getTransactionExecutor().execute(() -> {
            db.settingsDao().delete(settings);
        });
    }

    public void insertAll(Context context, Settings... settings) {
        AppDatabase db = AppDatabaseSingleton.getDatabase(context);
        db.getTransactionExecutor().execute(() -> {
            db.settingsDao().insertAll(settings);
        });
    }
}
