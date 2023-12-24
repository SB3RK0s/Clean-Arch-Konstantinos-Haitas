package com.learningwithmanos.uniexercise.heroes.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.learningwithmanos.uniexercise.heroes.repo.HeroRepository
import com.learningwithmanos.uniexercise.heroes.repo.HeroRepositoryImpl
import com.learningwithmanos.uniexercise.heroes.source.local.DBWrapper
import com.learningwithmanos.uniexercise.heroes.source.local.DBWrapperImpl
import com.learningwithmanos.uniexercise.heroes.source.local.HeroLocalSource
import com.learningwithmanos.uniexercise.heroes.source.local.HeroLocalSourceImpl
import com.learningwithmanos.uniexercise.heroes.source.local.database.HeroDao
import com.learningwithmanos.uniexercise.heroes.source.local.database.HeroDatabase
import com.learningwithmanos.uniexercise.heroes.source.remote.HeroRemoteSource
import com.learningwithmanos.uniexercise.heroes.source.remote.HeroRemoteSourceImpl
import com.learningwithmanos.uniexercise.heroes.source.remote.RestFrameworkWrapper
import com.learningwithmanos.uniexercise.heroes.source.remote.RestFrameworkWrapperImpl
import com.learningwithmanos.uniexercise.heroes.source.remote.api.MarvelApiService
import com.learningwithmanos.uniexercise.heroes.usecase.CheckForApiKeyChangeUseCase
import com.learningwithmanos.uniexercise.heroes.usecase.CheckForApiKeyChangeUseCaseImpl
import com.learningwithmanos.uniexercise.heroes.usecase.DeleteAllHeroesUseCase
import com.learningwithmanos.uniexercise.heroes.usecase.DeleteAllHeroesUseCaseImpl
import com.learningwithmanos.uniexercise.heroes.usecase.fetch.GetHeroesSortedByHighestNumberOfComicsUC
import com.learningwithmanos.uniexercise.heroes.usecase.fetch.GetHeroesSortedByHighestNumberOfComicsUCImpl
import com.learningwithmanos.uniexercise.heroes.usecase.fetch.GetHeroesSortedByNameUC
import com.learningwithmanos.uniexercise.heroes.usecase.fetch.GetHeroesSortedByNameUCImpl
import com.learningwithmanos.uniexercise.heroes.usecase.fetch.GetHeroesUC
import com.learningwithmanos.uniexercise.heroes.usecase.fetch.GetHeroesUCImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HeroesModule {

    // Usecase

    @Binds
    fun bindsGetHeroesUC(
        getHeroesUCImpl: GetHeroesUCImpl
    ): GetHeroesUC

    @Binds
    fun bindsGetHeroesSortedByNameUC(
        getHeroesSortedByNameUCImpl: GetHeroesSortedByNameUCImpl
    ): GetHeroesSortedByNameUC

    @Binds
    fun bindsGetHeroesSortedByHighestNumberOfComicsUC(
        getHeroesSortedByHighestNumberOfComicsUCImpl: GetHeroesSortedByHighestNumberOfComicsUCImpl
    ): GetHeroesSortedByHighestNumberOfComicsUC

    @Binds
    fun bindsCheckForApiKeyChangeUseCase(
        checkForApiKeyChangeUseCaseImpl: CheckForApiKeyChangeUseCaseImpl
    ): CheckForApiKeyChangeUseCase

    @Binds
    fun bindsDeleteAllHeroesUseCase (
        deleteAllHeroesUseCaseImpl: DeleteAllHeroesUseCaseImpl
    ): DeleteAllHeroesUseCase

    // Repository

    @Binds
    fun bindsHeroRepository(
        heroRepositoryImpl: HeroRepositoryImpl
    ): HeroRepository

    // Source

    @Binds
    @Singleton
    fun bindsHeroLocalSource(
        heroLocalSourceImpl: HeroLocalSourceImpl
    ): HeroLocalSource

    @Binds
    @Singleton
    fun bindsHeroRemoteSource(
        heroRemoteSourceImpl: HeroRemoteSourceImpl
    ): HeroRemoteSource

    // external frameworks

    @Binds
    @Singleton
    fun bindsRestFrameworkWrapper(
        restFrameworkWrapperImpl: RestFrameworkWrapperImpl
    ): RestFrameworkWrapper

    @Binds
    @Singleton
    fun bindsDBWrapper(
        dbWrapperImpl: DBWrapperImpl
    ): DBWrapper

}

@Module
@InstallIn(SingletonComponent::class)
object HeroDatabaseModule {

    // Database

    @Provides
    @Singleton
    fun provideHeroDatabase(
        app: Application
    ): HeroDatabase {
        return Room.databaseBuilder(app, HeroDatabase::class.java, "hero_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideHeroDao(
        db: HeroDatabase
    ): HeroDao {
        return db.heroDao()
    }

}


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //Retrofit

    @Provides
    @Singleton
    fun provideBaseUrl(): String {
        return "https://gateway.marvel.com/v1/public/"
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl:String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMarvelApiService(retrofit: Retrofit): MarvelApiService {
        return retrofit.create(MarvelApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object ApiKeysModule {

    //API Keys

    @Provides
    @Named("PublicKey")
    fun providePublicKey(sharedPreferences: SharedPreferences): String {
        return sharedPreferences.getString("publicKey", "") ?: ""
    }

    @Provides
    @Named("PrivateKey")
    fun providePrivateKey(sharedPreferences: SharedPreferences): String {
        return sharedPreferences.getString("privateKey", "") ?: ""
    }
    @Provides
    @Named("PublicKeyProvider")
    fun providePublicKeyProvider(sharedPreferences: SharedPreferences): () -> String {
        return { sharedPreferences.getString("publicKey", "") ?: "" }
    }

    @Provides
    @Named("PrivateKeyProvider")
    fun providePrivateKeyProvider(sharedPreferences: SharedPreferences): () -> String {
        return { sharedPreferences.getString("privateKey", "") ?: "" }
    }

    @Provides
    fun provideAreApiKeysFilled(
        @Named("PublicKeyProvider") publicKeyProvider: () -> String,
        @Named("PrivateKeyProvider") privateKeyProvider: () -> String
    ): () -> Boolean {
        return {
            val publicKey = publicKeyProvider()
            val privateKey = privateKeyProvider()
            publicKey.isNotEmpty() && privateKey.isNotEmpty()
        }
    }
}


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}


