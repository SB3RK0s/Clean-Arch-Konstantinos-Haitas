package com.learningwithmanos.uniexercise.heroes.source.remote

data class RHero(
    val id: Int,
    val name: String,
    val availableComics: Int,
    val imageUrl: String
)