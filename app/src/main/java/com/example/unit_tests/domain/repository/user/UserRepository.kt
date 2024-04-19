package com.example.unit_tests.domain.repository.user

import com.example.unit_tests.data.database.entity.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun checkIfUserExist(login: String, pwd: String): Boolean

    suspend fun addNewUser(user: User)

    fun getUserList(): Flow<List<User>>
}