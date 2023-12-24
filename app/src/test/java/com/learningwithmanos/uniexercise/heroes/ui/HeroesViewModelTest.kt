package com.learningwithmanos.uniexercise.heroes.ui

import com.learningwithmanos.uniexercise.heroes.data.Tab
import com.learningwithmanos.uniexercise.heroes.presenatation.viewmodels.HeroesViewModel
import com.learningwithmanos.uniexercise.heroes.usecase.fetch.GetHeroesSortedByHighestNumberOfComicsUC
import com.learningwithmanos.uniexercise.heroes.usecase.fetch.GetHeroesSortedByNameUC
import com.learningwithmanos.uniexercise.heroes.usecase.fetch.GetHeroesUC
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

@OptIn(ExperimentalCoroutinesApi::class)
class HeroesViewModelTest {

    private lateinit var heroesViewModel: HeroesViewModel

    private val getHeroesUCMock: GetHeroesUC = mock()
    private val getHeroesSortedByNameUCMock: GetHeroesSortedByNameUC = mock()
    private val getHeroesSortedByHighestNumberOfComicsUCMock: GetHeroesSortedByHighestNumberOfComicsUC = mock()

    @Before
    fun setUp() {
        heroesViewModel = HeroesViewModel(
            getHeroesUCMock,
            getHeroesSortedByNameUCMock,
            getHeroesSortedByHighestNumberOfComicsUCMock
        )
    }

    @Test
    fun `when getSelectedIndex is invoked with parameter then verify results`() {
        // when
        val actualResult1 = heroesViewModel.getSelectedIndex(Tab.Heroes)
        val actualResult2 = heroesViewModel.getSelectedIndex(Tab.SortedByNameHeroes)
        val actualResult3 = heroesViewModel.getSelectedIndex(Tab.SortedByComicHeroes)


        // then
        assertThat(actualResult1, equalTo(0))
        assertThat(actualResult2, equalTo(1))
        assertThat(actualResult3, equalTo(2))
    }

    @Test
    fun `given selected tab is Heroes when the selectedTabStateFlow is collected then assert result`() = runTest {
        // given
        heroesViewModel.selectTab(Tab.Heroes)

        // when
        backgroundScope.launch {
            heroesViewModel.selectedTabStateFlow.collectLatest {
                // then
                assertThat(it, equalTo(Tab.Heroes))
            }
        }
    }

    @Test
    fun `given selected tab is Heroes when the heroesStateFlow is collected then verify interactions`() = runTest {
        // given
        heroesViewModel.selectTab(Tab.Heroes)

        // when
        backgroundScope.launch {
            heroesViewModel.heroesStateFlow.collectLatest {
                // then
                verify(getHeroesUCMock).execute()
                verifyNoMoreInteractions(getHeroesSortedByNameUCMock)
                verifyNoMoreInteractions(getHeroesSortedByHighestNumberOfComicsUCMock)
            }
        }
    }

    @Test
    fun `given selected tab is SortedByNameHeroes when the selectedTabStateFlow is collected then assert result`() = runTest {
        // given
        heroesViewModel.selectTab(Tab.SortedByNameHeroes)

        // when
        backgroundScope.launch {
            heroesViewModel.selectedTabStateFlow.collectLatest {
                // then
                assertThat(it, equalTo(Tab.SortedByNameHeroes))
            }
        }
    }

    @Test
    fun `given selected tab is SortedByNameHeroes when the heroesStateFlow is collected then verify interactions`() = runTest {
        // given
        heroesViewModel.selectTab(Tab.SortedByNameHeroes)

        // when
        backgroundScope.launch {
            heroesViewModel.heroesStateFlow.collectLatest {
                // then
                verify(getHeroesSortedByNameUCMock).execute()
                verifyNoMoreInteractions(getHeroesUCMock)
                verifyNoMoreInteractions(getHeroesSortedByHighestNumberOfComicsUCMock)
            }
        }
    }

    @Test
    fun `given selected tab is SortedByComicHeroes when the selectedTabStateFlow is collected then assert result`() = runTest {
        // given
        heroesViewModel.selectTab(Tab.SortedByComicHeroes)

        // when
        backgroundScope.launch {
            heroesViewModel.selectedTabStateFlow.collectLatest {
                // then
                assertThat(it, equalTo(Tab.SortedByComicHeroes))
            }
        }
    }

    @Test
    fun `given selected tab is SortedByComicHeroes when the heroesStateFlow is collected then verify interactions`() = runTest {
        // given
        heroesViewModel.selectTab(Tab.SortedByComicHeroes)

        // when
        backgroundScope.launch {
            heroesViewModel.heroesStateFlow.collectLatest {
                // then
                verify(getHeroesSortedByHighestNumberOfComicsUCMock).execute()
                verifyNoMoreInteractions(getHeroesUCMock)
                verifyNoMoreInteractions(getHeroesSortedByNameUCMock)
            }
        }
    }

}