package com.learningwithmanos.uniexercise.heroes.source.local

import com.learningwithmanos.uniexercise.heroes.data.Hero
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Interface that wraps the local storage data framework that is used
 */
interface HeroLocalSource {

    /**
     * @return true if heroes are stored locally else false
     */
    fun isHeroDataStored(): Flow<Boolean>

    /**
     * Stores a list of heroes to the local data storage
     * @param heroes list of heroes to be stored
     */
    fun storeHeroes(heroes: List<Hero>)

    /**
     * @return the list of heroes stored at the local storage
     */
    fun getHeroes(): Flow<List<Hero>>
}

class HeroLocalSourceImpl @Inject constructor(
    private val dbWrapper: DBWrapper,
): HeroLocalSource {
    override fun isHeroDataStored(): Flow<Boolean> {
        return dbWrapper.isHeroDataStored()
    }

    override fun storeHeroes(heroes: List<Hero>) {
        dbWrapper.storeHeroes(heroes = heroes)
    }

    override fun getHeroes(): Flow<List<Hero>> {
        return dbWrapper.getHeroes()
    }

}