package com.example.unit_tests.presentation.screens.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unit_tests.data.database.entity.user.User
import com.example.unit_tests.domain.useCases.addNewUserUseCase
import com.example.unit_tests.domain.useCases.checkIfUserExistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val checkIfUserExistUseCase: checkIfUserExistUseCase,
    private val addNewUserUseCase: addNewUserUseCase
) : ViewModel() {

    private var _login = mutableStateOf("")
    var login: State<String> = _login

    private var _pwd = mutableStateOf("")
    var pwd: State<String> = _pwd

    private var _newUserlogin = mutableStateOf("")
    var newUserlogin: State<String> = _newUserlogin

    private var _newUserPwd = mutableStateOf("")
    var newUserPwd: State<String> = _newUserPwd

    private var _userName = mutableStateOf("")
    var userName: State<String> = _userName

    private val _isUserAutorize = mutableStateOf(false)
    var isUserAutorize: State<Boolean> = _isUserAutorize

    private val _isToastShown = mutableStateOf(false)
    var isToastShown: State<Boolean> = _isToastShown

    private val _toastText = mutableStateOf("")
    var toastText: State<String> = _toastText

    fun checkIfUserExist() = viewModelScope.launch {
        if (_login.value == "") {
            _toastText.value = "В поле логина введено пустое значение"
            _isToastShown.value = true
        }
        else if (_pwd.value == "") {
            _toastText.value = "В поле пароля введено пустое значение"
            _isToastShown.value = true
        }
        else {
            if (checkIfUserExistUseCase.invoke(_login.value, _pwd.value)) {
                _isUserAutorize.value = true
            } else {
                _toastText.value = "Такого пользователя не существует"
                _isToastShown.value = true
            }
        }
    }

    fun addNewUser() = viewModelScope.launch {
        if (_newUserlogin.value == "") {
            _toastText.value = "В поле логина введено пустое значение"
            _isToastShown.value = true
        }
        else if (_newUserPwd.value == "") {
            _toastText.value = "В поле пароля введено пустое значение"
            _isToastShown.value = true
        }
        else if (_userName.value == "") {
            _toastText.value = "В поле имени введено пустое значение"
            _isToastShown.value = true
        } else {
            addNewUserUseCase.invoke(
                User(
                    login = _newUserlogin.value,
                    pwd = _newUserPwd.value,
                    userName = _userName.value
                )
            )
            _toastText.value = "Новый пользователь добавлен"
            _isToastShown.value = true
        }
    }

    fun updateLoginField(loginText: String) {
        _login.value = loginText
    }

    fun updatePwdField(pwdText: String) {
        _pwd.value = pwdText
    }

    fun newLoginField(loginText: String) {
        _newUserlogin.value = loginText
    }

    fun newPwdField(pwdText: String) {
        _newUserPwd.value = pwdText
    }

    fun newNameField(name: String) {
        _userName.value = name
    }

    fun changeToastStatus(status: Boolean) {
        _isToastShown.value = status
    }
}