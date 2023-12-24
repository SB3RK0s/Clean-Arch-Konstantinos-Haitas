package com.learningwithmanos.uniexercise.heroes.source.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.learningwithmanos.uniexercise.heroes.source.local.LHero
import kotlinx.coroutines.flow.Flow

@Dao
interface HeroDao {

    @Query("SELECT * FROM LHero")
    fun getHeroes(): Flow<List<LHero>>

    @Query("SELECT COUNT(*) FROM LHero")
    suspend fun countHeroes(): Int

    @Query("DELETE FROM LHero")
    suspend fun deleteAllHeroes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroes(heroes: List<LHero>)

    @Update
    suspend fun updateHeroes(heroes: List<LHero>)

    @Delete
    suspend fun deleteHeroes(heroes: List<LHero>)

}
