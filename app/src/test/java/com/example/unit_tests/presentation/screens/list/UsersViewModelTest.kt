package com.example.unit_tests.presentation.screens.list

import com.example.unit_tests.data.database.entity.user.User
import com.example.unit_tests.domain.useCases.getUserListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock


class UsersViewModelTest {
    private val getUserListUseCase = mock<getUserListUseCase>()

    @Before
    fun set() {
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun reset() {
        Dispatchers.resetMain()
        Mockito.reset(getUserListUseCase)
    }

    @Test
    fun `getUsersList return 1 user`() {
        val userList = listOf(
            User(idUser = 1, login = "user", pwd = "password", userName = "name"),
            User(idUser = 2, login = "user1", pwd = "password1", userName = "name1"),
            User(idUser = 3, login = "user2", pwd = "password2", userName = "name2")
        )

        Mockito.`when`(getUserListUseCase.invoke()).thenReturn(flowOf(userList))
        val viewModel = UsersViewModel(getUserListUseCase)
        viewModel.changeFilterText("user2")

        assertEquals(userList[2], viewModel.FilteredUsers.value[0])
    }

    @Test
    fun `getUsersList returns users`() {
        val userList = listOf(
            User(idUser = 1, login = "user", pwd = "password", userName = "name"),
            User(idUser = 2, login = "user1", pwd = "password1", userName = "name1"),
            User(idUser = 3, login = "user21", pwd = "password2", userName = "name2")
        )

        Mockito.`when`(getUserListUseCase.invoke()).thenReturn(flowOf(userList))
        val viewModel = UsersViewModel(getUserListUseCase)
        viewModel.changeFilterText("1")

        assertEquals(2, viewModel.FilteredUsers.value.size)
    }

    @Test
    fun `getUsersList return empty list`() {
        val userList = listOf(
            User(idUser = 1, login = "user", pwd = "password", userName = "name"),
            User(idUser = 2, login = "user1", pwd = "password1", userName = "name1"),
            User(idUser = 3, login = "user2", pwd = "password2", userName = "name2")
        )
        val empty: List<User> = emptyList()
        Mockito.`when`(getUserListUseCase.invoke()).thenReturn(flowOf(userList))
        val viewModel = UsersViewModel(getUserListUseCase)
        viewModel.changeFilterText("Text")

        assertEquals(empty, viewModel.FilteredUsers.value)
    }

    @Test
    fun changeFilterText() {
        val viewModel = UsersViewModel(getUserListUseCase)

        viewModel.changeFilterText("Text")
        assertEquals("Text", viewModel.filterText.value)
    }

}