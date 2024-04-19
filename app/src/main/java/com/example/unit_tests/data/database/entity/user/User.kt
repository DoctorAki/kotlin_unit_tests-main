package com.example.unit_tests.data.database.entity.user

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    @NonNull val idUser: Int = 0,
    @NonNull val login: String,
    @NonNull val pwd: String,
    @NonNull val userName: String
)
