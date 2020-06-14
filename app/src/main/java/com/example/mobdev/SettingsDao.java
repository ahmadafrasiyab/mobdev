package com.example.mobdev;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
    @Dao
    public interface SettingsDao {
        @Query("SELECT * FROM settings")
        LiveData<List<Settings>> getAll();

        @Query("SELECT * FROM settings WHERE settingsId IN (:settingsId)")
        LiveData<List<Settings>> loadAllByIds(String[] settingsId);

//        @Query("SELECT * FROM settings WHERE first_name LIKE :firstName and last_name LIKE :lastName LIMIT 1")
//        LiveData<Settings> findByName(String firstName, String lastName);

        @Update
        void updateSettings(Settings... settings);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAll(Settings... settings);

        @Delete
        void delete(Settings setting);
    }

