package com.learningwithmanos.uniexercise.heroes.presenatation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.data.Tab
import com.learningwithmanos.uniexercise.heroes.presenatation.ui.state.HeroesUiState
import com.learningwithmanos.uniexercise.heroes.usecase.fetch.GetHeroesSortedByHighestNumberOfComicsUC
import com.learningwithmanos.uniexercise.heroes.usecase.fetch.GetHeroesSortedByNameUC
import com.learningwithmanos.uniexercise.heroes.usecase.fetch.GetHeroesUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HeroesViewModel @Inject constructor(
    private val getHeroesUC: GetHeroesUC,
    private val getHeroesSortedByNameUC: GetHeroesSortedByNameUC,
    private val getHeroesSortedByHighestNumberOfComicsUC: GetHeroesSortedByHighestNumberOfComicsUC,
) : ViewModel() {

    private var _selectedTabStateFlow: MutableStateFlow<Tab> = MutableStateFlow(Tab.Heroes)

    val selectedTabStateFlow: StateFlow<Tab> = _selectedTabStateFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _selectedTabStateFlow.value
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val heroesStateFlow: StateFlow<HeroesUiState> = selectedTabStateFlow.flatMapLatest { selectedTab ->
        when (selectedTab) {
            Tab.Heroes -> getHeroesUC.execute()
            Tab.SortedByNameHeroes -> getHeroesSortedByNameUC.execute()
            Tab.SortedByComicHeroes -> getHeroesSortedByHighestNumberOfComicsUC.execute()
        }.map { result ->
            if (result.isSuccess) {
                HeroesUiState(heroes = result.getOrNull()?.map { it.mapHeroToHeroTileModel() } ?: emptyList())
            } else {
                HeroesUiState(errorMessage = "Press the settings button to enter the API keys\n${result.exceptionOrNull()?.message}")
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HeroesUiState()
    )

    /**
     * Utilises corresponding UC to retrieve data based on the selectedTab.
     * @param selectedTab
     */
    fun getSelectedIndex(selectedTab: Tab): Int {
        return when (selectedTab) {
            Tab.Heroes -> 0
            Tab.SortedByNameHeroes -> 1
            Tab.SortedByComicHeroes -> 2
        }
    }

    /**
     * Sets the selected tab
     */
    fun selectTab(tab: Tab) {
        _selectedTabStateFlow.value = tab
    }

}

data class HeroTileModel(
    val title: String,
    val imageUrl: String,
)

fun Hero.mapHeroToHeroTileModel(): HeroTileModel {
    return HeroTileModel(
        title = "$name\n$availableComics Comics Available",
        imageUrl = imageUrl
    )
}