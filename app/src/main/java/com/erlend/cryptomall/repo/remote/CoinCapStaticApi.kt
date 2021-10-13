/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.repo.remote

import com.erlend.cryptomall.CryptoMallApp
import com.erlend.cryptomall.model.entities.AssetDtoServerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

// Getting asset icons. See AppModule for base  url
interface CoinCapStaticApi {
    @Headers("Authorization: Bearer ${CryptoMallApp.API_KEY}")
    @GET("/assets/icons/{id}@2x.png")
    fun getIcon(
        @Path("id") id: String
    ): Call<AssetDtoServerResponse>
}