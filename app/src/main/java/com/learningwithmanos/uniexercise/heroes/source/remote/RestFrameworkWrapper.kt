package com.learningwithmanos.uniexercise.heroes.source.remote

import com.learningwithmanos.uniexercise.heroes.source.remote.api.MarvelApiService
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Named


interface RestFrameworkWrapper {
    suspend fun getHeroes(): List<RHero>
}

class RestFrameworkWrapperImpl @Inject constructor(
    private val marvelApiService: MarvelApiService,
    @Named("PublicKeyProvider") private val publicKeyProvider: () -> String,
    @Named("PrivateKeyProvider") private val privateKeyProvider: () -> String
) : RestFrameworkWrapper {

    override suspend fun getHeroes(): List<RHero> {
        val publicKey = publicKeyProvider()
        val privateKey = privateKeyProvider()
        val timestamp = System.currentTimeMillis()
        val hash = md5(timestamp.toString() + privateKey + publicKey)

        val response = marvelApiService.getCharacters(
            orderBy = "modified",
            limit = 20,
            apiKey = publicKey,
            timestamp = timestamp,
            hash = hash
        )

        if (response.isSuccessful) {
            return response.body()?.data?.results?.map { marvelCharacter ->
                RHero(
                    id = marvelCharacter.id,
                    name = marvelCharacter.name,
                    availableComics = marvelCharacter.comics.available,
                    imageUrl = "${marvelCharacter.thumbnail.path}/portrait_incredible.${marvelCharacter.thumbnail.extension}"
                )
            } ?: emptyList()
        } else {
            throw Exception("${response.code()} - ${response.message()}")
        }
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return md.digest(input.toByteArray()).joinToString("") { byte -> "%02x".format(byte) }
    }
}