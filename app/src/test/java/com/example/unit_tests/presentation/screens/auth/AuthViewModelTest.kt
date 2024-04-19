package com.example.unit_tests.presentation.screens.auth

import com.example.unit_tests.domain.useCases.addNewUserUseCase
import com.example.unit_tests.domain.useCases.checkIfUserExistUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Assertions.assertFalse


class AuthViewModelTest {
    private val checkIfUserExistUseCase = mock<checkIfUserExistUseCase>()
    private val addNewUserUseCase = mock<addNewUserUseCase>()

    @Before
    fun set() {
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun reset() {
        Dispatchers.resetMain()
        Mockito.reset(checkIfUserExistUseCase)
        Mockito.reset(addNewUserUseCase)
    }

    @Test
    fun `1,1) checkIfUserExist with empty login`() {
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)

        viewModel.updateLoginField("")
        viewModel.checkIfUserExist()

        assertTrue(viewModel.isToastShown.value)
        assertEquals("В поле логина введено пустое значение", viewModel.toastText.value)
    }

    @Test
    fun `1,2) checkIfUserExist with empty pwd`() {
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)

        viewModel.updateLoginField("login")
        viewModel.updatePwdField("")
        viewModel.checkIfUserExist()

        assertEquals("В поле пароля введено пустое значение", viewModel.toastText.value)
        assertTrue(viewModel.isToastShown.value)
    }

    @Test
    fun `1,3) checkIfUserExist with data not exist user`() = runBlocking {
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)
        val login = "login"
        val pwd = "password"

        viewModel.updateLoginField(login)
        viewModel.updatePwdField(pwd)

        Mockito.`when`(checkIfUserExistUseCase.invoke("login", "password")).thenReturn(false)

        viewModel.checkIfUserExist()

        assertEquals("Такого пользователя не существует", viewModel.toastText.value)
        assertTrue(viewModel.isToastShown.value)
        assertFalse(viewModel.isUserAutorize.value)
    }

    @Test
    fun `1,4) checkIfUserExist with valid data`() = runBlocking {
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)
        val login = "login"
        val pwd = "password"

        viewModel.updateLoginField(login)
        viewModel.updatePwdField(pwd)

        Mockito.`when`(checkIfUserExistUseCase.invoke("login", "password")).thenReturn(true)

        viewModel.checkIfUserExist()

        assertTrue(viewModel.isUserAutorize.value)
    }

    @Test
    fun `2,1) addNewUser with empty login`() {
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)
        val login = ""
        val pwd = "password"
        val userName = "name"

        viewModel.newLoginField(login)
        viewModel.newPwdField(pwd)
        viewModel.newNameField(userName)
        viewModel.addNewUser()

        assertEquals("В поле логина введено пустое значение", viewModel.toastText.value)
        assertTrue(viewModel.isToastShown.value)
    }

    @Test
    fun `2,2) addNewUser with empty password`() {
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)
        val login = "login"
        val pwd = ""
        val userName = "name"

        viewModel.newLoginField(login)
        viewModel.newPwdField(pwd)
        viewModel.newNameField(userName)
        viewModel.addNewUser()

        assertEquals("В поле пароля введено пустое значение", viewModel.toastText.value)
        assertTrue(viewModel.isToastShown.value)
    }

    @Test
    fun `2,3) addNewUser with empty name`() {
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)
        val login = "login"
        val pwd = "password"
        val userName = ""

        viewModel.newLoginField(login)
        viewModel.newPwdField(pwd)
        viewModel.newNameField(userName)
        viewModel.addNewUser()

        assertEquals("В поле имени введено пустое значение", viewModel.toastText.value)
        assertTrue(viewModel.isToastShown.value)
    }

    @Test
    fun `2,4) addNewUser with valid data`() = runBlocking {
        val viewModel = AuthViewModel(checkIfUserExistUseCase, addNewUserUseCase)
        val login = "login"
        val pwd = "password"
        val userName = "name"

        viewModel.newLoginField(login)
        viewModel.newPwdField(pwd)
        viewModel.newNameField(userName)
        viewModel.addNewUser()

        assertEquals("Новый пользователь добавлен", viewModel.toastText.value)
        assertTrue(viewModel.isToastShown.value)
    }
}