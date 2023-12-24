package com.learningwithmanos.uniexercise.heroes.repo

import android.util.Log
import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.source.local.HeroLocalSource
import com.learningwithmanos.uniexercise.heroes.source.remote.HeroRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

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
    private val areApiKeysFilled: () -> Boolean
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
            Log.e("HeroRepository", "Trying to fetch heroes from remote source")
            val heroList = heroRemoteSource.getHeroes()

            Log.e("HeroRepository", "Heroes fetched successfully")
            heroLocalSource.storeHeroes(heroList)

            Log.e("HeroRepository", "Saving heroes to local source")
            flowOf(Result.success(heroList))
        } catch (e: Exception) {
            Log.e("HeroRepository", "Error fetching heroes from remote source: ${e.message}")
            flowOf(Result.failure(e))
        }
    }

    private fun fetchHeroesFromLocal(): Flow<Result<List<Hero>>> {
        Log.e("HeroRepository", "Fetching heroes from local source")
        return heroLocalSource.getHeroes().map { Result.success(it) }
    }

    private fun logAndReturnError(): Flow<Result<List<Hero>>> {
        val message = "API keys are empty"
        Log.e("HeroRepository", message)
        return flowOf(Result.failure(Exception(message)))
    }

    override suspend fun deleteAllHeroes() {
        heroLocalSource.deleteAllHeroes()
        Log.e("HeroRepository", "Local Heroes Deleted")
    }
}
