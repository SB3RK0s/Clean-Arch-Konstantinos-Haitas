package com.learningwithmanos.uniexercise.heroes.presenatation.viewmodels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learningwithmanos.uniexercise.heroes.usecase.CheckForApiKeyChangeUseCase
import com.learningwithmanos.uniexercise.heroes.usecase.DeleteAllHeroesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class KeyEntryViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @Named("PublicKey") var publicKey: String,
    @Named("PrivateKey") var privateKey: String,
    private val checkForApiKeyChangeUseCase: CheckForApiKeyChangeUseCase,
    private val deleteAllHeroesUseCase: DeleteAllHeroesUseCase
): ViewModel() {

    fun onSaveButtonClicked(newPublicKey: String, newPrivateKey: String) {
        viewModelScope.launch {
            Log.e("HeroRepository", "checkForApiKeyChangeUseCase")
            if (checkForApiKeyChangeUseCase.execute(newPublicKey, newPrivateKey)) {
                Log.e("HeroRepository", "updatePublicKey")
                updatePublicKey(newPublicKey)
                Log.e("HeroRepository", "updatePrivateKey")
                updatePrivateKey(newPrivateKey)
                deleteAllHeroesUseCase.execute()
            }
        }
    }

    private fun updatePublicKey(newKey: String) {
        publicKey = newKey
        saveKeys()
    }

    private fun updatePrivateKey(newKey: String) {
        privateKey = newKey
        saveKeys()
    }

    private fun saveKeys() {
        sharedPreferences.edit().apply {
            putString("publicKey", publicKey)
            putString("privateKey", privateKey)
            apply()
            Log.e("HeroRepository", "Public Key: $publicKey Private Key: $privateKey")
        }
    }
}