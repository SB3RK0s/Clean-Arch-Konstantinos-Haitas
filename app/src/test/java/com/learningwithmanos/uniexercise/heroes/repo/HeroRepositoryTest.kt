package com.learningwithmanos.uniexercise.heroes.repo

import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.source.local.HeroLocalSource
import com.learningwithmanos.uniexercise.heroes.source.remote.HeroRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verifyNoMoreInteractions
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class HeroRepositoryImplTest {

    private lateinit var heroRepositoryImpl: HeroRepositoryImpl

    private val heroRemoteSourceMock: HeroRemoteSource = mock()
    private val heroLocalSourceMock: HeroLocalSource = mock()

    private val dummyHeroData = listOf(
        Hero(
            id = 1,
            name = "A-Bomb (HAS)",
            availableComics = 4,
            imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg",
        ),
        Hero(
            id = 2,
            name = "Absorbing Man",
            availableComics = 99,
            imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/1/b0/5269678709fb7.jpg",
        )
    )

    @Before
    fun setUp() {
        heroRepositoryImpl = HeroRepositoryImpl(
            heroRemoteSourceMock,
            heroLocalSourceMock
        )
    }

    @Test
    fun `given no data are stored when getHeroes is invoked then verify api call and store to DB`() = runTest{
        // given
        given(heroLocalSourceMock.isHeroDataStored()).willReturn(flowOf(false))
        given(heroRemoteSourceMock.getHeroes()).willReturn(dummyHeroData)

        // when
        heroRepositoryImpl.getHeroes().collect { actualHeroes ->
            // then
            assertThat(actualHeroes, equalTo(dummyHeroData))
            verify(heroLocalSourceMock).isHeroDataStored()
            verify(heroRemoteSourceMock).getHeroes()
            verify(heroLocalSourceMock).storeHeroes(dummyHeroData)
            verifyNoMoreInteractions(heroLocalSourceMock)
        }
    }

    @Test
    fun `given data are stored when getHeroes is invoked then verify retrieving of data from DB`() = runTest{
        // given
        given(heroLocalSourceMock.isHeroDataStored()).willReturn(flowOf(true))
        given(heroLocalSourceMock.getHeroes()).willReturn(flowOf(dummyHeroData))

        // when
        heroRepositoryImpl.getHeroes().collect { actualHeroes ->
            // then
            assertThat(actualHeroes, equalTo(dummyHeroData))
            verifyNoMoreInteractions(heroRemoteSourceMock)
            verify(heroLocalSourceMock).isHeroDataStored()
            verify(heroLocalSourceMock).getHeroes()
        }

    }

}