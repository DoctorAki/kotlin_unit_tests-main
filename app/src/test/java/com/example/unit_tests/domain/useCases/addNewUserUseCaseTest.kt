package com.example.unit_tests.domain.useCases

import com.example.unit_tests.data.database.entity.user.User
import com.example.unit_tests.domain.repository.user.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertThrows

import org.mockito.Mockito
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock

class addNewUserUseCaseTest {
    private val userRepository = mock<UserRepository>()

    @After
    fun reset(){
        Mockito.reset(userRepository)
    }

    @Test
    fun `add user with valid data`() = runBlocking {

        val user = User(idUser = 1, login = "user", pwd = "password", userName = "name")
        addNewUserUseCase(userRepository).invoke(user)

        Mockito.verify(userRepository, Mockito.times(1)).addNewUser(User(idUser = 1, login = "user", pwd = "password", userName = "name"))
    }

    @Test
    fun `add user with invalid data`() = runBlocking{
        val user = User(idUser = 1, login = "", pwd = "", userName = "")

        addNewUserUseCase(userRepository).invoke(user)

        val exception = RuntimeException("Invalid data")
        doThrow(exception).`when`(userRepository).addNewUser(user)
        val thrown = assertThrows<RuntimeException> {
            addNewUserUseCase(userRepository).invoke(user)
        }
        Assertions.assertEquals(exception, thrown)
    }

}
