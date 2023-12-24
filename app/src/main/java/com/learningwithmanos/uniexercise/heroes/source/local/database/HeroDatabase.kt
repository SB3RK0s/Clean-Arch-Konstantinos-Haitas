package com.learningwithmanos.uniexercise.heroes.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.learningwithmanos.uniexercise.heroes.source.local.LHero

@Database(
    entities = [LHero::class],
    version = 1,
    exportSchema = false,
)


abstract class HeroDatabase: RoomDatabase() {

    abstract fun heroDao(): HeroDao
}