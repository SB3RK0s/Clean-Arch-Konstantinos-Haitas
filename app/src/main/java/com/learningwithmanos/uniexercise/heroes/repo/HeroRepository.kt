package com.learningwithmanos.uniexercise.heroes.repo

import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.source.local.HeroLocalSource
import com.learningwithmanos.uniexercise.heroes.source.remote.HeroRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

/**
 * A repository interface that is used to coordinate the usage of the LocalSource and the
 * RemoteSource.
 */
interface HeroRepository {

    /**
     * In the scope of this method it is decided from which source the data should be retrieved from.
     * Retrieve from local if data are stored, otherwise retrieve data from remote and also store
     * the data to the local.
     *
     * @return list of heroes
     */
    suspend fun getHeroes(): Flow<Result<List<Hero>>>
    suspend fun deleteAllHeroes()
}

class HeroRepositoryImpl @Inject constructor(
    private val heroRemoteSource: HeroRemoteSource,
    private val heroLocalSource: HeroLocalSource,
    @Named("AreApiKeysFilledProvider") private val areApiKeysFilled: () -> Boolean
) : HeroRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getHeroes(): Flow<Result<List<Hero>>> {
        return heroLocalSource.isHeroDataStored().flatMapLatest { isHeroDataStored ->
            when {
                !isHeroDataStored && areApiKeysFilled() -> fetchHeroesFromRemote()
                !isHeroDataStored -> logAndReturnError()
                else -> fetchHeroesFromLocal()
            }
        }
    }

    private suspend fun fetchHeroesFromRemote(): Flow<Result<List<Hero>>> {
        return try {

            val heroList = heroRemoteSource.getHeroes()
            heroLocalSource.storeHeroes(heroList)
            flowOf(Result.success(heroList))

        } catch (e: Exception) {
            flowOf(Result.failure(e))
        }
    }

    private fun fetchHeroesFromLocal(): Flow<Result<List<Hero>>> {
        return heroLocalSource.getHeroes().map { Result.success(it) }
    }

    private fun logAndReturnError(): Flow<Result<List<Hero>>> {
        val message = "API keys are empty"
        return flowOf(Result.failure(Exception(message)))
    }

    override suspend fun deleteAllHeroes() {
        heroLocalSource.deleteAllHeroes()
    }
}
