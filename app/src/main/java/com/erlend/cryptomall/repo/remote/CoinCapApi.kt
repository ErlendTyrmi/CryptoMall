package com.erlend.cryptomall.repo.remote

import com.erlend.cryptomall.CryptoMallApp
import com.erlend.cryptomall.repo.entities.Asset
import com.erlend.cryptomall.repo.entities.AssetHistory
import com.erlend.cryptomall.repo.entities.Assets
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface CoinCapApi {

    @Headers("Authorization: Bearer ${CryptoMallApp.API_KEY}")
    @GET("assets")
    fun Assets(
    ) : Call<Assets>

    @Headers("Authorization: Bearer ${CryptoMallApp.API_KEY}")
    @GET("assets/{id}")
    fun getAsset(@Path("id") id : String
    ) : Call<Asset>

    //assets/bitcoin/history?interval=d1
    @Headers("Authorization: Bearer ${CryptoMallApp.API_KEY}")
    @GET("assets/{id}/history?interval=d1")
    fun getHistory(@Path("id") id : String
    ) : Call<AssetHistory>

    // Builder
    companion object {
        const val BASE_URL = "https://api.coincap.io/v2/"

        fun create() : CoinCapApi {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(CoinCapApi::class.java)
        }
    }
}

