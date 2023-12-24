package com.learningwithmanos.uniexercise.heroes.source.local

import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.data.toDomainModel
import com.learningwithmanos.uniexercise.heroes.data.toLHero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Interface that wraps the local storage data framework that is used
 */
interface HeroLocalSource {

    /**
     * @return true if heroes are stored locally else false
     */
    suspend fun isHeroDataStored(): Flow<Boolean>

    /**
     * Stores a list of heroes to the local data storage
     * @param heroes list of heroes to be stored
     */
    suspend fun storeHeroes(heroes: List<Hero>)

    /**
     * @return the list of heroes stored at the local storage
     */
    fun getHeroes(): Flow<List<Hero>>

    /**
     * Deletes the list of heroes from the local data storage
     */
    suspend fun deleteAllHeroes()
}

class HeroLocalSourceImpl @Inject constructor(
    private val dbWrapper: DBWrapper,
): HeroLocalSource {
    override suspend fun isHeroDataStored(): Flow<Boolean> {
        return dbWrapper.isHeroDataStored()
    }

    override suspend fun storeHeroes(heroes: List<Hero>) {
        dbWrapper.storeHeroes(heroes = heroes.map { it.toLHero() })
    }

    override fun getHeroes(): Flow<List<Hero>> {
        return dbWrapper.getHeroes().map { lHeroes ->lHeroes.map { it.toDomainModel() }}
    }

    override suspend fun deleteAllHeroes() {
        dbWrapper.deleteAllHeroes()
    }
}