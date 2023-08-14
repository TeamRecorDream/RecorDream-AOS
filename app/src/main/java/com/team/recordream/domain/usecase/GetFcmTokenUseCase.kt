package com.team.recordream.domain.usecase

import com.team.recordream.domain.repository.AuthRepository
import javax.inject.Inject

class GetFcmTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(setFCMToken: (String) -> Unit) =
        authRepository.getFcmToken(setFCMToken)
}
