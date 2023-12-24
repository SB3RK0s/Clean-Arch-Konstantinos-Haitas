package com.learningwithmanos.uniexercise.heroes.source.remote

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class HeroRemoteSourceImplTest {

    private lateinit var heroRemoteSourceImpl: HeroRemoteSourceImpl

    private val restFrameworkWrapperMock: RestFrameworkWrapper = mock()

    @Before
    fun setUp() {
        heroRemoteSourceImpl = HeroRemoteSourceImpl(
            restFrameworkWrapperMock
        )
    }

    @Test
    fun `when invoking getHeroes verify results and interactions`() = runTest{
        // when
        heroRemoteSourceImpl.getHeroes()

        // then
        verify(restFrameworkWrapperMock).getHeroes()
    }
}