package com.example.unit_tests.data.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.unit_tests.data.database.entity.user.User

@Database(
    entities = [
        User::class
    ],
    version = 1,
    exportSchema = true
)
abstract class TestsDatabase : RoomDatabase() {
    abstract fun testsDao(): TestsDao

}