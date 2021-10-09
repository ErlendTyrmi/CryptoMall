package com.erlend.cryptomall.depInjection

import com.erlend.cryptomall.repo.remote.CoinCapApi
import com.erlend.cryptomall.repo.remote.CoinCapStaticApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    // CoinCapStaticApi gets the asset icons
    @Provides
    fun provideCoinCapStaticApi(): CoinCapStaticApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://static.coincap.io/")
            .build().create(CoinCapStaticApi::class.java)
    }
}


