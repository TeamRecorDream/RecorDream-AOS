package com.recodream_aos.recordream.domain.usecase

import com.recodream_aos.recordream.domain.repository.AuthRepository
import javax.inject.Inject

class GetFcmTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(setFCMToken: (String) -> Unit) =
        authRepository.getFcmToken(setFCMToken)
}
