package com.learningwithmanos.uniexercise.heroes.usecase

import com.learningwithmanos.uniexercise.heroes.repo.HeroRepository
import javax.inject.Inject

interface DeleteAllHeroesUseCase {
    suspend fun execute()
}

class DeleteAllHeroesUseCaseImpl @Inject constructor(
    private val heroRepository: HeroRepository
) : DeleteAllHeroesUseCase {
    override suspend fun execute() {
        heroRepository.deleteAllHeroes()
    }
}