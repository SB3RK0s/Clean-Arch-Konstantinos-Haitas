/*package com.learningwithmanos.uniexercise.heroes.source.remote

import com.learningwithmanos.uniexercise.heroes.source.remote.api.MarvelApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class RestFrameworkWrapperTest {
    private lateinit var restFrameworkWrapper: RestFrameworkWrapperImpl
    private val marvelApiService = mock(MarvelApiService::class.java)
    private val publicKeyProvider = { "publicKey" }
    private val privateKeyProvider = { "privateKey" }

    @Before
    fun setUp() {
        restFrameworkWrapper = RestFrameworkWrapperImpl(
            marvelApiService,
            publicKeyProvider,
            privateKeyProvider
        )
    }

    @Test
    fun `getHeroes returns list on successful response`() = runBlockingTest {
        // Mock API response
        val mockResponse = mock(Response::class.java) as Response<MarvelApiResponse>
        `when`(mockResponse.isSuccessful).thenReturn(true)
        `when`(mockResponse.body()).thenReturn(/* Mocked response body */)
        `when`(marvelApiService.getCharacters(anyString(), anyInt(), anyString(), anyLong(), anyString()))
            .thenReturn(mockResponse)

        // Call method
        val result = restFrameworkWrapper.getHeroes()

        // Verify results
        assertNotNull(result)
        // Additional assertions on the result
    }

    @Test(expected = Exception::class)
    fun `getHeroes throws exception on API failure`() = runBlockingTest {
        // Mock API error response
        val mockResponse = mock(Response::class.java) as Response<MarvelApiResponse>
        `when`(mockResponse.isSuccessful).thenReturn(false)
        `when`(marvelApiService.getCharacters(anyString(), anyInt(), anyString(), anyLong(), anyString()))
            .thenReturn(mockResponse)

        // Call method
        restFrameworkWrapper.getHeroes()

        // Exception is expected
    }

    // Additional tests can be added here, e.g., testing the md5 function, handling of null values, etc.
}
}
 */