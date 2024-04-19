package com.example.unit_tests.data.repository.user

import com.example.unit_tests.data.database.TestsDao
import com.example.unit_tests.data.database.entity.user.User
import com.example.unit_tests.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dao: TestsDao): UserRepository {
    override suspend fun checkIfUserExist(login: String, pwd: String): Boolean {
        return dao.getUser(login, pwd) !== null
    }

    override suspend fun addNewUser(user: User) {
        dao.addNewUser(user)
    }

    override fun getUserList(): Flow<List<User>> {
        return dao.getUserList()
    }

}