package com.erlend.cryptomall.depInjection

import com.erlend.cryptomall.repo.remote.CoinCapApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object appModule{

@Provides
fun provideCoinApi(): CoinCapApi {
    return CoinCapApi.create()
}
}

