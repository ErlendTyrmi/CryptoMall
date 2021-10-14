/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erlend.cryptomall.data.dto.toAsset
import com.erlend.cryptomall.domain.model.entities.Asset
import com.erlend.cryptomall.domain.model.entities.AssetDtoListServerResponse
import com.erlend.cryptomall.domain.model.entities.AssetDtoServerResponse
import com.erlend.cryptomall.data.repo.local.LocalDao
import com.erlend.cryptomall.data.repo.remote.CoinCapApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AssetViewModel @Inject constructor(
    private val api: CoinCapApi,
    val db: LocalDao
) : ViewModel() {

    suspend fun getAssetsDb(): List<Asset> {
        return db.getAssets()
    }

    suspend fun refreshAssets(): List<Asset> {
        return db.getAssets()
    }

    // Get assets and store in room eg. on page refresh
    fun getAssets() {
        api.getAssets().enqueue(object : Callback<AssetDtoListServerResponse> {
            override fun onResponse(
                call: Call<AssetDtoListServerResponse>,
                response: Response<AssetDtoListServerResponse>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    val responseBody = response.body()!!
                    viewModelScope.launch(Dispatchers.IO) {
                        responseBody.data.forEach {
                            Log.d(TAG, it.symbol)
                            db.insertAssets(it.toAsset())
                        }
                        db.getAssets().forEach{
                            Log.d(TAG, it.name + " " + it.priceUsd)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<AssetDtoListServerResponse>, t: Throwable) {
                Log.d(TAG, "Failed to retrieve data: " + t.localizedMessage)
            }
        })
    }

    //Get updated plain asset
    fun getAsset(id: String) {
        api.getAsset(id).enqueue(object : Callback<AssetDtoServerResponse> {
            override fun onResponse(
                call: Call<AssetDtoServerResponse>,
                response: Response<AssetDtoServerResponse>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    val responseBody = response.body()!!
                    viewModelScope.launch(Dispatchers.IO) {
                        db.insertAssets(responseBody.data.toAsset())
                    }
                }
            }

            override fun onFailure(call: Call<AssetDtoServerResponse>, t: Throwable) {
                Log.d(TAG, "Failed to retrieve data: " + t.localizedMessage)
            }
        })
    }

    // Get icons
    fun getIcons(){
    }
}