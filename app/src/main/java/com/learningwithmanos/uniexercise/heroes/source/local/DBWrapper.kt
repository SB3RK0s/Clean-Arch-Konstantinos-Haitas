package com.learningwithmanos.uniexercise.heroes.source.local

import com.learningwithmanos.uniexercise.heroes.source.local.database.HeroDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface DBWrapper {
    suspend fun isHeroDataStored(): Flow<Boolean>
    suspend fun storeHeroes(heroes: List<LHero>)
    fun getHeroes(): Flow<List<LHero>>
    suspend fun deleteAllHeroes()
}

class DBWrapperImpl @Inject constructor(
    private val heroDao: HeroDao
) : DBWrapper {
    override suspend fun isHeroDataStored(): Flow<Boolean> {
        return flowOf(heroDao.countHeroes() > 0)
    }

    override suspend fun storeHeroes(heroes: List<LHero>) {
        heroDao.insertHeroes(heroes)
    }

    override fun getHeroes(): Flow<List<LHero>> {
        return heroDao.getHeroes()
    }

    override suspend fun deleteAllHeroes() {
        heroDao.deleteAllHeroes()
    }
}