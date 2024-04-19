package com.example.unit_tests.domain.useCases


import com.example.unit_tests.data.database.entity.user.User
import com.example.unit_tests.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.jupiter.api.Assertions
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock


class getUserListUseCaseTest {

    val userRepository = mock<UserRepository>()

    @After
    fun reset(){
        Mockito.reset(userRepository)
    }

    @Test
    fun `check user list`() = runBlocking{
        val userList = listOf(
            User(idUser = 1, login = "user", pwd = "password", userName = "name"),
            User(idUser = 2, login = "user1", pwd = "password1", userName = "name1"),
            User(idUser = 3, login = "user2", pwd = "password2", userName = "name2")
        )

        Mockito.`when`(userRepository.getUserList()).thenReturn(flowOf(userList))

        val result = userRepository.getUserList().first()

        Assertions.assertEquals(3, result.size)
        Assertions.assertEquals(userList, result)
    }

    @Test
    fun `check empty list`() = runBlocking {

        Mockito.`when`(userRepository.getUserList()).thenReturn(flowOf(emptyList()))

        val getList = getUserListUseCase(userRepository)
        val result = mutableListOf<User>()

        getList().collect { userList ->
            result.addAll(userList)
        }

        Assertions.assertTrue(result.isEmpty())
    }

}

