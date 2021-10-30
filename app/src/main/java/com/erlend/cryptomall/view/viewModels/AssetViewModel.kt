/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erlend.cryptomall.repo.dto.dtoConversion.toAsset
import com.erlend.cryptomall.domain.model.entities.Asset
import com.erlend.cryptomall.repo.dto.AssetDtoListServerResponse
import com.erlend.cryptomall.repo.dto.AssetDtoServerResponse
import com.erlend.cryptomall.repo.local.LocalDao
import com.erlend.cryptomall.repo.remote.CoinCapApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
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
    private val tag = "AssetViewModel"

    //region Livedata (State)

    // Option 1 of binding to UI: Collect the flow and throw into a MutableLiveData
    private val assets = MutableLiveData(listOf<Asset>(Asset("_", "_", "_", "_", "_")))

    init {
        // Pull assets from remote, store in local
        viewModelScope.launch {
            // Pulls assets to db
            pullAssetsRemote()
        }

        // Get assets from local, observe as LiveData
        viewModelScope.launch {
            db.getAssets().collect { assets.value = it }
            /*db.getAssets().collect {
                Log.d(tag, "Collected from room. Showing first in list: " + it[0].name)
            }*/
        }
    }

    //endregion

    // Local DB
    fun getAssetsLocal(): MutableLiveData<List<Asset>> {
        return assets
    }

    fun getAssetLocal(symbol: String): Flow<Asset> {
        return db.getAsset(symbol).distinctUntilChanged()
    }

    // Get assets from REMOTE and store in room, eg. on page refresh
    fun pullAssetsRemote() {
        viewModelScope.launch(Dispatchers.IO) {
            api.getAssets().enqueue(object : Callback<AssetDtoListServerResponse> {
                override fun onResponse(
                    call: Call<AssetDtoListServerResponse>,
                    response: Response<AssetDtoListServerResponse>
                ) {
                    if (response.code() == 200 && response.body() != null) {
                        val responseBody = response.body()!!
                        viewModelScope.launch(Dispatchers.IO) {
                            responseBody.data.forEach {
                                db.insertAssets(it.toAsset())
                            }
                            Log.d(tag, "Pulled " + responseBody.data.size + " assets from remote.")
                        }
                    }
                }

                override fun onFailure(call: Call<AssetDtoListServerResponse>, t: Throwable) {
                    Log.d(tag, "Failed to retrieve data: " + t.localizedMessage)
                }
            })
        }
    }

    //Get updated plain asset
    fun pullAssetRemote(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
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
                    Log.d(tag, "Failed to retrieve data: " + t.localizedMessage)
                }
            })
        }
    }
}