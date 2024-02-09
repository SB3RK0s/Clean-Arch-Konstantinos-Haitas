package com.learningwithmanos.uniexercise.heroes.source.remote

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class HeroRemoteSourceImplTest {

    private lateinit var heroRemoteSourceImpl: HeroRemoteSourceImpl

    private val restFrameworkWrapperMock: RestFrameworkWrapper = mock()

    private val dummyHeroData = listOf(
        RHero(
            id = 1,
            name = "A-Bomb (HAS)",
            availableComics = 4,
            imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg",
        ),
        RHero(
            id = 2,
            name = "Absorbing Man",
            availableComics = 99,
            imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/1/b0/5269678709fb7.jpg",
        )
    )

    @Before
    fun setUp() {
        heroRemoteSourceImpl = HeroRemoteSourceImpl(
            restFrameworkWrapperMock
        )
    }

    @Test
    fun `when invoking getHeroes verify results and interactions`() = runTest{
        // given
        given(restFrameworkWrapperMock.getHeroes()).willReturn(dummyHeroData)

        // when
        heroRemoteSourceImpl.getHeroes()

        // then
        verify(restFrameworkWrapperMock).getHeroes()
    }
}
