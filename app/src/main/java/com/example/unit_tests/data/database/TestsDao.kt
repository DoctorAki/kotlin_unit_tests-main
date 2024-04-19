package com.example.unit_tests.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unit_tests.data.database.entity.user.User
import kotlinx.coroutines.flow.Flow

@Dao
interface TestsDao {
    @Query("SELECT * FROM User WHERE login = :login and pwd = :pwd LIMIT 1")
    suspend fun getUser(login: String, pwd: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewUser(user: User)

    @Query("SELECT * FROM User")
    fun getUserList(): Flow<List<User>>
}