package com.erlend.cryptomall.repo.remote

import com.erlend.cryptomall.CryptoMallApp
import com.erlend.cryptomall.repo.entities.Asset
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface CoinCapStaticApi {
    @Headers("Authorization: Bearer ${CryptoMallApp.API_KEY}")
    @GET("/assets/icons/{id}@2x.png")
    fun getIcon(
        @Path("id") id: String
    ): Call<Asset>

    // Builder
    companion object {
        const val BASE_URL = "https://static.coincap.io/"

        fun create() : CoinCapStaticApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(CoinCapStaticApi::class.java)
        }
    }
}