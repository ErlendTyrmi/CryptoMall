/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.repo.remote

import com.erlend.cryptomall.CryptoMallApp
import com.erlend.cryptomall.model.entities.AssetDtoServerResponse
import com.erlend.cryptomall.model.entities.RemoteAssetHistory
import com.erlend.cryptomall.model.entities.AssetDtoListServerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

// Getting asset data. See AppModule for base  url
interface CoinCapApi {

    @Headers("Authorization: Bearer ${CryptoMallApp.API_KEY}")
    @GET("assets")
    fun getAssets(
    ) : Call<AssetDtoListServerResponse>

    @Headers("Authorization: Bearer ${CryptoMallApp.API_KEY}")
    @GET("assets/{id}")
    fun getAsset(@Path("id") id : String
    ) : Call<AssetDtoServerResponse>

    @Headers("Authorization: Bearer ${CryptoMallApp.API_KEY}")
    @GET("assets/{id}/history?interval=d1")
    fun getHistory(@Path("id") id : String
    ) : Call<RemoteAssetHistory>
}

