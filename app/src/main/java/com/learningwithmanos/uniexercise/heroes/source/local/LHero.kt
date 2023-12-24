package com.learningwithmanos.uniexercise.heroes.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LHero(
    @PrimaryKey
    val id: Int,
    val name: String,
    val availableComics: Int,
    val imageUrl: String
)