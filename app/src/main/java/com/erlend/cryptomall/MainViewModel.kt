/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erlend.cryptomall.model.dto.toAsset
import com.erlend.cryptomall.model.entities.*
import com.erlend.cryptomall.repo.local.LocalDao
import com.erlend.cryptomall.repo.remote.CoinCapApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

const val TAG = "MainViewModel: "

@HiltViewModel
class MainViewModel @Inject constructor(// Repos
    private val api: CoinCapApi, val db: LocalDao
) : ViewModel() {

    // Flow - Asset list screen 2
    // Note: Back button should close the app
    // Nav from here to

    // Get assets and store in room on page refresh
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


    // Get icons
    fun getIcons(){
    }

    // check if account exists and is initiated
    fun checkAccountInit(){

    }

    // db is null or account is null? Create and add 10.000
    fun setupAccount( ) : Int{
        return 0
    }

    // Sum up points from all owned assets, in USD
    fun getSumPoints( accountId: String ){
    }

    // Flow screen 3 - Owned assets overview
    // Note: has "transactions" button

    // Show a list of specified owned assets by largest sum in USD
    fun getOwnedAssets(){
    }

    // Flow screen 7 - Transactions
    // Note: Back button to 3

    fun getTransactions() : List<AssetTransaction> {
        return listOf(AssetTransaction("", 2, 1.2, 33.1, "", ""))
    }

    // FLow screen 4: Asset page
    // An asset is clicked and might be traded. Update asset
    // Buttons to buy and sell visible when action is possible

    //Get updated plain asset
    fun getAsset(id: String) {
        api.getAsset(id).enqueue(object : Callback<AssetDtoServerResponse> {
            override fun onResponse(
                call: Call<AssetDtoServerResponse>,
                response: Response<AssetDtoServerResponse>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    val responseBody = response.body()!!

                        //db.insertAssets(responseBody.data.toAsset())

                }
            }

            override fun onFailure(call: Call<AssetDtoServerResponse>, t: Throwable) {
                Log.d(TAG, "Failed to retrieve data: " + t.localizedMessage)
            }
        })
    }


    fun getAssetInAccount(symbol: String){
    }

    // Graph Bonus? Get history asset list and draw
    fun getAssetHistory(){
    }

    // FLow screen 5 - Buy
    // Show dollars left
    // Enter dollars - update crypto amount
    // Show error if user enters too much
    // Back to 4

    // Click changes account value og dollars and said crypto
    fun buy( symbol: String, amount: Double, paid: Double ){
        // Atomic:
        // Account dollars go down
        // [symbol] goes up
    }

    // Flow Screen 6 Sell
    // Enter crypto, update dollars
    // Same layout as 5
    // Also back to 4, much reuse here

    fun sell(symbol: String, amount: Double, earned: Double){
        // Atomic:
        // Dollars go down
        // Symbol goes up
    }
}

