package com.example.unit_tests.presentation.screens.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unit_tests.data.database.entity.user.User
import com.example.unit_tests.domain.useCases.addNewUserUseCase
import com.example.unit_tests.domain.useCases.checkIfUserExistUseCase
import com.example.unit_tests.domain.useCases.getUserListUseCase
import com.example.unit_tests.presentation.screens.auth.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUserListUseCase: getUserListUseCase
) : ViewModel() {

    private var _Users = MutableStateFlow(listOf<User>())
    var Users: StateFlow<List<User>> = _Users.asStateFlow()

    private var _FilteredUsers = MutableStateFlow(listOf<User>())
    var FilteredUsers: StateFlow<List<User>> = _FilteredUsers.asStateFlow()

    private var _filterText = MutableStateFlow("")
    var filterText: StateFlow<String> = _filterText.asStateFlow()

    init {
        getUsersList()
    }

    private fun getUsersList() = viewModelScope.launch {
        getUserListUseCase.invoke().collectLatest {
            _Users.value = it
            filterUsers()
        }
    }

    private fun filterUsers() {
        if (_filterText.value == "") {
            _FilteredUsers.value = _Users.value
        }
        else {
            _FilteredUsers.value = _Users.value.filter {
                it.login.contains(_filterText.value, ignoreCase = true)
            }
        }
    }

    fun changeFilterText(text: String) {
        _filterText.value = text
        filterUsers()
    }
}