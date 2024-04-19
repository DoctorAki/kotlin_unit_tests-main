package com.example.unit_tests.domain.useCases

import com.example.unit_tests.domain.repository.user.UserRepository
import javax.inject.Inject

class getUserListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.getUserList()
}