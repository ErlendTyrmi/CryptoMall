/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erlend.cryptomall.model.dto.toAsset
import com.erlend.cryptomall.model.entities.Asset
import com.erlend.cryptomall.model.entities.AssetDtoListServerResponse
import com.erlend.cryptomall.model.entities.AssetDtoServerResponse
import com.erlend.cryptomall.repo.local.LocalDao
import com.erlend.cryptomall.repo.remote.CoinCapApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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

    fun getAssetsDb(): Flow<List<Asset>> {
        return db.getAssets()
    }

    fun refreshAssets(): Flow<List<Asset>> {
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
                        db.getAssets().collect { Log.d(TAG, it.toString()) }
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
                    db.insertAssets(responseBody.data.toAsset())
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