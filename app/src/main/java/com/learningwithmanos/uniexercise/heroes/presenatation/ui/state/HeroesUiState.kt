package com.learningwithmanos.uniexercise.heroes.presenatation.ui.state

import com.learningwithmanos.uniexercise.heroes.presenatation.viewmodels.HeroTileModel

data class HeroesUiState(
    val heroes: List<HeroTileModel> = emptyList(),
    val errorMessage: String? = null
)
