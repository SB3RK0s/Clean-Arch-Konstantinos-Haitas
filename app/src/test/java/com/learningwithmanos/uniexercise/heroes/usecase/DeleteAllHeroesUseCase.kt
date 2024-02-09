package com.learningwithmanos.uniexercise.heroes.usecase

import com.learningwithmanos.uniexercise.heroes.repo.HeroRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteAllHeroesUseCaseImplTest {

    private lateinit var deleteAllHeroesUseCaseImpl: DeleteAllHeroesUseCaseImpl

    private val heroRepositoryMock: HeroRepository = mock()

    @Before
    fun setUp() {
        deleteAllHeroesUseCaseImpl = DeleteAllHeroesUseCaseImpl(
            heroRepositoryMock
        )
    }

    @Test
    fun `when execute is invoked then verify interactions`() = runTest {
        // when
        deleteAllHeroesUseCaseImpl.execute()

        // then
        verify(heroRepositoryMock).deleteAllHeroes()
    }
}
