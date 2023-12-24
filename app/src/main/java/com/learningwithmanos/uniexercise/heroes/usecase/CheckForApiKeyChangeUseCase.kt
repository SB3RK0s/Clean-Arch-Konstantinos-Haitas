package com.learningwithmanos.uniexercise.heroes.usecase

import android.content.SharedPreferences
import javax.inject.Inject

interface CheckForApiKeyChangeUseCase {
    suspend fun execute(newPublicKey: String, newPrivateKey: String): Boolean
}

class CheckForApiKeyChangeUseCaseImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : CheckForApiKeyChangeUseCase {
    override suspend fun execute(newPublicKey: String, newPrivateKey: String): Boolean {
        val oldPublicKey = sharedPreferences.getString("publicKey", "")
        val oldPrivateKey = sharedPreferences.getString("privateKey", "")
        return newPublicKey != oldPublicKey || newPrivateKey != oldPrivateKey
    }
}

