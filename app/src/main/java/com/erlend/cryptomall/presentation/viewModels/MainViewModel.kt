/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.erlend.cryptomall.domain.model.entities.*
import com.erlend.cryptomall.data.repo.local.LocalDao
import com.erlend.cryptomall.data.repo.remote.CoinCapApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val TAG = "MainViewModel: "

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: CoinCapApi,
    val db: LocalDao
) : ViewModel() {

    // Flow - Asset list screen 2
    // Note: Back button should close the app
    // Nav from here to

    // Get assets and store in room eg. on page refresh
    fun getAssets() {
        // This is in assetviewmodel
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
        // This is in assetviewmodel
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

