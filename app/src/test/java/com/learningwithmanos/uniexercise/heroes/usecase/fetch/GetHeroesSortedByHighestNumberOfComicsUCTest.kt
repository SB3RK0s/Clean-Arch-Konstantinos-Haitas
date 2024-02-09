package com.learningwithmanos.uniexercise.heroes.usecase.fetch

import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.repo.HeroRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class GetHeroesSortedByHighestNumberOfComicsUCImplTest {

    private lateinit var getHeroesSortedByHighestNumberOfComicsUCImpl: GetHeroesSortedByHighestNumberOfComicsUCImpl

    private val heroRepositoryMock: HeroRepository = mock()

    @Before
    fun setUp() {
        getHeroesSortedByHighestNumberOfComicsUCImpl = GetHeroesSortedByHighestNumberOfComicsUCImpl(
            heroRepositoryMock
        )
    }

    @Test
    fun `when execute is invoked then verify interactions`()= runTest{
        // given
        val heroesList = dummyHeroList().getOrThrow()
        given(heroRepositoryMock.getHeroes()).willReturn(flowOf(Result.success(heroesList)))
        val expectedHeroesList = sortHeroListByComicsNumber(heroesList)

        // when
        val resultFlow: Flow<Result<List<Hero>>> = getHeroesSortedByHighestNumberOfComicsUCImpl.execute()

        // then
        resultFlow.collect { result ->
            result.onSuccess { actualHeroesList ->
                assertThat(actualHeroesList, equalTo(expectedHeroesList))
            }
        }
        verify(heroRepositoryMock).getHeroes()
    }

    private fun dummyHeroList(): Result<List<Hero>> {
        val heroesList = listOf(
            Hero(
                id = 1,
                name = "Absorbing Man",
                availableComics = 99,
                imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/1/b0/5269678709fb7.jpg",
            ),
            Hero(
                id = 2,
                name = "3-D Man",
                availableComics = 12,
                imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg",
            ),
            Hero(
                id = 3,
                name = "Aaron Stack",
                availableComics = 14,
                imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg",
            ),
            Hero(
                id = 4,
                name = "A.I.M.",
                availableComics = 53,
                imageUrl = "http://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec.jpg",
            )
        )
        return Result.success(heroesList)
    }

    private fun sortHeroListByComicsNumber(heroesList: List<Hero>): List<Hero> {
        return heroesList.sortedByDescending { it.availableComics }
    }

}
