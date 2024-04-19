package com.example.unit_tests.domain.useCases

import com.example.unit_tests.domain.repository.user.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.jupiter.api.Assertions
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.kotlin.mock

class checkIfUserExistUseCaseTest {

    private val userRepository = mock<UserRepository>()

    @After
    fun reset(){
        Mockito.reset(userRepository)
    }

    @Test
    fun `check exist user`() = runBlocking {
        val login = "user"
        val pwd = "password"

        Mockito.`when`(userRepository.checkIfUserExist(login, pwd)).thenReturn(true)

        val actual = checkIfUserExistUseCase(userRepository).invoke("user", "password")
        val expected = true
        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `check not exist user`() = runBlocking {
        val login = "user"
        val pwd = "password"

        Mockito.`when`(userRepository.checkIfUserExist(login, pwd)).thenReturn(false)

        val actual = checkIfUserExistUseCase(userRepository).invoke("user", "password")
        val expected = false

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `check not exist user with invalid data`() = runBlocking {
        val login = ""
        val pwd = ""
        val expected = RuntimeException("Invalid data")

        Mockito.`when`(userRepository.checkIfUserExist(login, pwd)).thenThrow(expected)

        val actual = assertThrows<RuntimeException> {
            checkIfUserExistUseCase(userRepository).invoke("", "")
        }

        Assertions.assertEquals(expected, actual)
    }


}