/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erlend.cryptomall.domain.model.entities.Asset
import com.erlend.cryptomall.repo.dto.AssetDtoServerResponse
import com.erlend.cryptomall.repo.dto.dtoConversion.toAsset
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
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

// Handles logic for views in the "trade" package

@HiltViewModel
class TradeViewModel @Inject constructor(
    private val api: CoinCapApi,
    val db: LocalDao
) : ViewModel() {
    private val TAG = "TradeViewModel"

    // Mutable live data
    private val asset = MutableLiveData(Asset())

    fun observeAsset(symbol: String) {

        // Get assets from local, observe as LiveData
        viewModelScope.launch {
            db.getAsset(symbol).distinctUntilChanged().collect { asset.value = it }
        }

    }

    fun getAssetLocal(): LiveData<Asset> {
        return asset
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
                        Log.d(TAG, "Pulled one asset from remote.")
                    }
                }

                override fun onFailure(call: Call<AssetDtoServerResponse>, t: Throwable) {
                    Log.d(TAG, "Failed to retrieve data: " + t.localizedMessage)
                }
            })
        }
    }
}