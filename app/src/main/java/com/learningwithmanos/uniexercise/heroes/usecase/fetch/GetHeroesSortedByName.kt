package com.learningwithmanos.uniexercise.heroes.usecase.fetch

import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.repo.HeroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * UC used to retrieve a list of heroes sorted by the name of heroes
 */
interface GetHeroesSortedByNameUC {
    suspend fun execute(): Flow<Result<List<Hero>>>
}

class GetHeroesSortedByNameUCImpl @Inject constructor(
    private val heroRepository: HeroRepository
): GetHeroesSortedByNameUC {
    override suspend fun execute(): Flow<Result<List<Hero>>> {
        return heroRepository.getHeroes().map { result ->
            result.map { list -> list.sortedBy { it.name } }
        }
    }
}