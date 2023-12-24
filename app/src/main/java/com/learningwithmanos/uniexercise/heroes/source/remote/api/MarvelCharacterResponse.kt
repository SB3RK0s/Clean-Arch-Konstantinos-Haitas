package com.learningwithmanos.uniexercise.heroes.source.remote.api

import com.google.gson.annotations.SerializedName

data class MarvelCharactersResponse(
    val code: Int,
    val status: String,
    val data: MarvelData
)

data class MarvelData(
    val results: List<MarvelCharacter>
)

data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    val comics: Comics
) {
    val imageUrl get() = "${thumbnail.path}.${thumbnail.extension}"
}

data class Thumbnail(
    val path: String,
    val extension: String
)

data class Comics(
    val available: Int
)
