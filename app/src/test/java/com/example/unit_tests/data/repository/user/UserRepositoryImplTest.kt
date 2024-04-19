package com.example.unit_tests.data.repository.user


import com.example.unit_tests.data.database.TestsDao
import com.example.unit_tests.data.database.entity.user.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.mockito.Mockito
import org.mockito.kotlin.mock


class UserRepositoryImplTest {

    private val testsDao = mock<TestsDao>()

    @After
    fun reset(){
        Mockito.reset(testsDao)
    }
    @Test
    fun `check exist user`() = runBlocking {

        val userRepository = UserRepositoryImpl(testsDao)
        val user = User(idUser = 1, login = "user", pwd = "password", userName = "name")

        Mockito.`when`(testsDao.getUser("user", "password")).thenReturn(user)

        val result = userRepository.checkIfUserExist("user", "password")

        assertTrue(result)
    }

    @Test
    fun `check not exist user`() = runBlocking {

        val userRepository = UserRepositoryImpl(testsDao)
        val result = userRepository.checkIfUserExist("notExistUser", "password")

        assertFalse(result)
    }

    @Test
    fun `add user`() = runBlocking {

        val userRepository = UserRepositoryImpl(testsDao)
        val user = User(idUser = 1, login = "user", pwd = "password", userName = "name")
        userRepository.addNewUser(user)

        Mockito.verify(testsDao).addNewUser(user)
    }

    @Test
    fun `get user list`() = runBlocking {

        val userList = listOf(
            User(idUser = 1, login = "user", pwd = "password", userName = "name"),
            User(idUser = 2, login = "user1", pwd = "password1", userName = "name1"),
            User(idUser = 3, login = "user2", pwd = "password2", userName = "name2")
        )

        Mockito.`when`(testsDao.getUserList()).thenReturn(flowOf(userList))

        val userRepository = UserRepositoryImpl(testsDao)
        val resultUserList = userRepository.getUserList().first()

        assertEquals(userList, resultUserList)
    }
}