package com.learningwithmanos.uniexercise.heroes.usecase

import com.learningwithmanos.uniexercise.heroes.repo.HeroRepository
import com.learningwithmanos.uniexercise.heroes.usecase.fetch.GetHeroesUCImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class GetHeroesUCImplTest {

    private lateinit var getHeroesUCImpl: GetHeroesUCImpl

    private val heroRepositoryMock: HeroRepository = mock()


    @Before
    fun setUp() {
        getHeroesUCImpl = GetHeroesUCImpl(
            heroRepositoryMock
        )
    }

    @Test
    fun `when execute is invoked then verify interactions`()= runTest{
        // when
        getHeroesUCImpl.execute()

        // then
        verify(heroRepositoryMock).getHeroes()
    }

}