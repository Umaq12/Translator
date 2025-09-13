package com.example.covertervk.di

import android.content.Context
import androidx.room.Room
import com.example.covertervk.data.local.AppDatabase
import com.example.covertervk.data.local.TranslationDao
import com.example.covertervk.data.remote.ExchangeApi
import com.example.covertervk.data.repository.TranslationRepositoryImpl
import com.example.covertervk.domain.repository.TranslationRepository
import com.example.covertervk.domain.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExchangeApi(): ExchangeApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL) // https://dictionary.skyeng.ru/
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTranslationRepository(
        api: ExchangeApi,
        dao: TranslationDao
    ): TranslationRepository {
        return TranslationRepositoryImpl(api, dao)
    }

    // --- Room ---
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app.db"
        ).build()

    @Provides
    @Singleton
    fun provideTranslationDao(db: AppDatabase): TranslationDao = db.translationDao()
}
