package com.learningwithmanos.uniexercise.heroes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Hero(
    @PrimaryKey
    val id: Int,
    val name: String,
    val availableComics: Int,
    val imageUrl: String
)