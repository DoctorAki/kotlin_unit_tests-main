package com.example.unit_tests.domain.useCases

import com.example.unit_tests.domain.repository.user.UserRepository
import javax.inject.Inject

class checkIfUserExistUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(login: String, pwd: String) = userRepository.checkIfUserExist(login, pwd)
}