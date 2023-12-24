package com.learningwithmanos.uniexercise.heroes.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Hero::class],
    version = 1,
    exportSchema = false,
)


abstract class HeroDatabase: RoomDatabase() {

    abstract fun heroDao(): HeroDao
}