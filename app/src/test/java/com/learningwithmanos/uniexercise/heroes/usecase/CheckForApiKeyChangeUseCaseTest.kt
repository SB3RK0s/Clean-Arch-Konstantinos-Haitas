package com.learningwithmanos.uniexercise.heroes.usecase

import android.content.SharedPreferences
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class CheckForApiKeyChangeUseCaseImplTest {

    private lateinit var checkForApiKeyChangeUseCaseImpl: CheckForApiKeyChangeUseCaseImpl

    private val sharedPreferencesMock: SharedPreferences = mock()

    @Before
    fun setUp() {
        checkForApiKeyChangeUseCaseImpl = CheckForApiKeyChangeUseCaseImpl(
            sharedPreferencesMock
        )
    }

    @Test
    fun `execute returns true when API keys have changed`()= runTest{
        // given
        val oldPublicKey = "oldPublicKey"
        val oldPrivateKey = "oldPrivateKey"
        val newPublicKey = "newPublicKey"
        val newPrivateKey = "newPrivateKey"
        given(sharedPreferencesMock.getString("publicKey", "")).willReturn(oldPublicKey)
        given(sharedPreferencesMock.getString("privateKey", "")).willReturn(oldPrivateKey)

        // when
        val result = checkForApiKeyChangeUseCaseImpl.execute(newPublicKey, newPrivateKey)

        // then
        assertTrue(result)
    }

    @Test
    fun `execute returns false when API keys have not changed`()= runTest{
        // given
        val publicKey = "samePublicKey"
        val privateKey = "samePrivateKey"
        given(sharedPreferencesMock.getString("publicKey", "")).willReturn(publicKey)
        given(sharedPreferencesMock.getString("privateKey", "")).willReturn(privateKey)

        // when
        val result = checkForApiKeyChangeUseCaseImpl.execute(publicKey, privateKey)


        // then
        assertFalse(result)
    }
}