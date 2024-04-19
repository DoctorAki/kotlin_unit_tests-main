package com.example.unit_tests.domain.useCases

import com.example.unit_tests.data.database.entity.user.User
import com.example.unit_tests.domain.repository.user.UserRepository
import javax.inject.Inject

class addNewUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(user: User) = userRepository.addNewUser(user)
}