package com.learningwithmanos.uniexercise.heroes.source.remote

import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.source.remote.api.MarvelApiService
import javax.inject.Inject
import java.security.MessageDigest


interface RestFrameworkWrapper{
    suspend fun getHeroes(): List<Hero>
}

class RestFrameworkWrapperImpl @Inject constructor(
    private val marvelApiService: MarvelApiService
): RestFrameworkWrapper {

    override suspend fun getHeroes(): List<Hero> {

        val timestamp = System.currentTimeMillis()
        val hash = md5(timestamp.toString() + PRIVATE_KEY + PUBLIC_KEY)

        val response = marvelApiService.getCharacters(
            orderBy = "modified",
            limit = 30,
            apiKey = PUBLIC_KEY,
            timestamp = timestamp,
            hash = hash
        )

        if (response.isSuccessful) {
            return response.body()?.data?.results?.map { marvelCharacter ->
                Hero(
                    id = marvelCharacter.id,
                    name = marvelCharacter.name,
                    availableComics = marvelCharacter.comics.available,
                    imageUrl = "${marvelCharacter.thumbnail.path}/portrait_incredible.${marvelCharacter.thumbnail.extension}"
                )
            } ?: emptyList()
        } else {
            // Consider a more nuanced approach to error handling
            throw Exception("Error fetching heroes: ${response.code()} - ${response.message()}")
        }
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return md.digest(input.toByteArray()).joinToString("") { byte -> "%02x".format(byte) }
    }

    /**
     * Add key injection for security
     */

    companion object {
        private const val PUBLIC_KEY = "a3380d17e4b7cb86656acf89a94ce519" // Replace with your public key
        private const val PRIVATE_KEY = "2a935511f75e0769d67b23db127b3cb1774e38fc" // Replace with your private key
    }
}