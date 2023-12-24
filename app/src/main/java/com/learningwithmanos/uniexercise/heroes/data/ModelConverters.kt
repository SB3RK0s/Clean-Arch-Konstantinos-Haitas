package com.learningwithmanos.uniexercise.heroes.data

import com.learningwithmanos.uniexercise.heroes.source.local.LHero
import com.learningwithmanos.uniexercise.heroes.source.remote.RHero

// Converts RHero (Network Model) to Hero (Domain Model)
fun RHero.toDomainModel(): Hero {
    return Hero(
        id = this.id,
        name = this.name,
        availableComics = this.availableComics,
        imageUrl = this.imageUrl
    )
}

// Converts LHero (Database Model) to Hero (Domain Model)
fun LHero.toDomainModel(): Hero {
    return Hero(
        id = this.id,
        name = this.name,
        availableComics = this.availableComics,
        imageUrl = this.imageUrl
    )
}

// Converts Hero (Domain Model) to LHero (Database Model)
fun Hero.toLHero(): LHero {
    return LHero(
        id = this.id,
        name = this.name,
        availableComics = this.availableComics,
        imageUrl = this.imageUrl
    )
}