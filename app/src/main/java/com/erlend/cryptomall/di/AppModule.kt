/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.di

import android.content.Context
import androidx.room.Room
import com.erlend.cryptomall.repo.local.LocalDao
import com.erlend.cryptomall.repo.local.LocalDatabase
import com.erlend.cryptomall.repo.remote.CoinCapApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Main module to provide @ Singleton-level
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // CoinCapApi gets the asset data
    @Provides
    fun provideCoinCapApi(): CoinCapApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.coincap.io/v2/")
            .build().create(CoinCapApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAssetDatabase(@ApplicationContext appContext: Context): LocalDatabase {
        return Room.databaseBuilder(
            appContext,
            LocalDatabase::class.java,
            "roomAssetDb"
        ).build()
    }

    @Provides
    fun provideAssetDao(localDatabase: LocalDatabase): LocalDao {
        return localDatabase.localDao()
    }


    /*// CoinCapStaticApi gets the asset icons
    @Provides
    fun provideCoinCapStaticApi(): CoinCapStaticApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://static.coincap.io/")
            .build().create(CoinCapStaticApi::class.java)
    }*/

    /*// CoinCapStaticApi gets the asset icons DEFINED IN APPLICATION CLASS
    @Provides
    fun provideImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build()
    }*/
}

