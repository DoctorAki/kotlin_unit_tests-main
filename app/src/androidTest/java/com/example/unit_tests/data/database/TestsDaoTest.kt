package com.example.unit_tests.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.unit_tests.data.database.entity.user.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TestsDaoTest {
    private lateinit var testsDao: TestsDao
    private lateinit var db: TestsDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context, TestsDatabase::class.java
        ).build()
        testsDao = db.testsDao()
    }

    @After
    fun close() {
        db.close()
    }

    @Test
    fun addAndGetUserTest() = runBlocking {
        val user = User(idUser = 1, login = "user", pwd = "password", userName = "name")

        testsDao.addNewUser(user)

        val reciveUser = testsDao.getUser("user", "password")

        assertEquals(user, reciveUser)
    }

    @Test
    fun getUserListTest() = runBlocking {
        val userList = listOf(
            User(idUser = 1, login = "user", pwd = "password", userName = "name"),
            User(idUser = 2, login = "user1", pwd = "password1", userName = "name1"),
            User(idUser = 3, login = "user2", pwd = "password2", userName = "name2")
        )

        userList.forEach {
            testsDao.addNewUser(it)
        }

        val result = testsDao.getUserList().first()

        assertEquals(userList, result)
    }
}