package com.example.unit_tests.presentation.screens.auth

import android.widget.Toast
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import es.dmoral.toasty.Toasty

@Composable
fun AuthScreen(
    navHostController: NavHostController
) {

    val authViewModel: AuthViewModel = hiltViewModel()
    val login = authViewModel.login
    val pwd = authViewModel.pwd
    val newLogin = authViewModel.newUserlogin
    val newPwd = authViewModel.newUserPwd
    val newName = authViewModel.userName
    val isUserAutorize = authViewModel.isUserAutorize
    val isToastShown = authViewModel.isToastShown
    val toastText = authViewModel.toastText

    if (isUserAutorize.value) {
        navHostController.navigate("UsersList")
    }

    if (isToastShown.value) {
        Toasty.info(LocalContext.current, toastText.value, Toast.LENGTH_SHORT).show()
        authViewModel.changeToastStatus(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Чтобы войти в приложение, сначала добавьте пользователя")
        Text(text = "Логин")
        TextField(
            value = login.value,
            onValueChange = { authViewModel.updateLoginField(it) },
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = "Пароль")
        TextField(
            value = pwd.value,
            onValueChange = { authViewModel.updatePwdField(it) },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            authViewModel.checkIfUserExist()
        }) {
            Text(text = "Войти")
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Добавьте пользователя тут:")
        Text(text = "Логин")
        TextField(
            value = newLogin.value,
            onValueChange = { authViewModel.newLoginField(it) },
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = "Пароль")
        TextField(
            value = newPwd.value,
            onValueChange = { authViewModel.newPwdField(it) },
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = "Имя")
        TextField(
            value = newName.value,
            onValueChange = { authViewModel.newNameField(it) },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            authViewModel.addNewUser()
        }) {
            Text(text = "Добавить пользователя")
        }
    }
}