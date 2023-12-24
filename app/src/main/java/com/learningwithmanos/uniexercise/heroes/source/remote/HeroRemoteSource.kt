package com.learningwithmanos.uniexercise.heroes.source.remote

import com.learningwithmanos.uniexercise.heroes.data.Hero
import javax.inject.Inject

/**
 * Interface that wraps the framework that is used for the Rest calls
 */
interface HeroRemoteSource {

    /**
     * @return retrieves the a list of heroes from a certain endpoint
     */
    suspend fun getHeroes(): List<Hero>
}

class HeroRemoteSourceImpl @Inject constructor(
    private val restFrameworkWrapper: RestFrameworkWrapper,
): HeroRemoteSource {

    override suspend fun getHeroes(): List<Hero> {
        return restFrameworkWrapper.getHeroes()
    }

}

