package com.learningwithmanos.uniexercise.heroes.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HeroDao {

    @Query("SELECT * FROM Hero")
    fun getHeroes(): Flow<List<Hero>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHero(heroes: List<Hero>)

    @Update
    suspend fun updateHero(heroes: List<Hero>)

    @Delete
    suspend fun deleteHero(heroes: List<Hero>)

}
