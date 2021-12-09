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
import com.erlend.cryptomall.domain.model.entities.AssetAmount
import com.erlend.cryptomall.domain.model.entities.AssetTransaction
import com.erlend.cryptomall.repo.dto.AssetDtoServerResponse
import com.erlend.cryptomall.repo.dto.dtoConversion.toAsset
import com.erlend.cryptomall.repo.local.LocalDao
import com.erlend.cryptomall.repo.remote.CoinCapApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import okhttp3.internal.wait
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
    private val referenceAsset = "USD"

    // Mutable live data
    // Owned amount of one asset
    private val _amountOwned = MutableLiveData<String>("0")
    val amountOwned: LiveData<String>
        get() = _amountOwned

    private val asset = MutableLiveData(Asset())

    // Account ID - Created on first boot
    private val _liquids = MutableLiveData<String>()
    val liquids: LiveData<String>
        get() = _liquids

    fun observeAssetLocal(symbol: String) {
        // Get assets from local, observe as LiveData
        viewModelScope.launch {
            db.getAsset(symbol).distinctUntilChanged().collect { asset.value = it }
        }
    }

    // Get the asset stored in this ViewModel
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
                            db.insertAsset(responseBody.data.toAsset())
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

    // Get owned amount of an asset from the database
    private suspend fun pullOwnedAmount(symbol: String): String {
            val id: UUID = db.getAccount()!!.accountId
            val owned = db.getOwnedAsset(id, symbol).first().amountOwned
            Log.d(TAG, "getOwnedAmount: $owned")
            // Update owned variable
            return owned
    }

    fun pullLiquids() {
        viewModelScope.launch{
            val id: UUID = db.getAccount()!!.accountId
            _liquids.value = db.getOwnedAsset(id, referenceAsset)[0].amountOwned
        }
    }


    fun buy(buySymbol: String, amount: String) {
        // Calculate price
        val price = (asset.value?.priceUsd?.toBigDecimal()?.times(amount.toBigDecimal()))

        viewModelScope.launch{
            // Check draft
            val dollars = pullOwnedAmount(referenceAsset).toBigDecimal()
            Log.d(TAG, "buy: You own $dollars dollars")
            // TODO: check if enough dough for this purchase

            // Make Transaction
            makeTransaction(buySymbol, referenceAsset, amount, price.toString())
        }
    }
    fun sell(sellSymbol: String,  amount: String) {
        // Calculate price
        // Check draft
        // Make Transaction
    }

    // Selling is buying dollars :-)
    private fun makeTransaction(buySymbol: String, sellSymbol: String, bought: String, sold: String){
        viewModelScope.launch {
            val id: UUID = db.getAccount()!!.accountId

            // Store the transaction (Not changing account holdings)
            db.insertTransaction(AssetTransaction(
                accountId = id,
                timestamp = System.currentTimeMillis(),
                inAmount = bought,
                inCurrencySymbol = buySymbol,
                outAmount = sold,
                outCurrencySymbol = sellSymbol,
            ))
            Log.d(TAG, "makeTransaction: paying $sold to buy $bought coins of $buySymbol")

            // Pay
            val oldPaidBalance = db.getOwnedAsset(id, sellSymbol).first()
            val newPaidBalance = oldPaidBalance.amountOwned.toBigDecimal() - sold.toBigDecimal()
            db.deleteOwnedAsset(oldPaidBalance)
            db.insertOwnedAsset(AssetAmount(id, sellSymbol, newPaidBalance.toString()))

            // Update bought asset
            lateinit var newBoughtBalance: BigDecimal

            val boughtAssetAsList = db.getOwnedAsset(id, buySymbol)
            newBoughtBalance = if (boughtAssetAsList.isNotEmpty()){
                val oldBoughtBalance = boughtAssetAsList.first()
                oldBoughtBalance.amountOwned.toBigDecimal() + bought.toBigDecimal()
            } else {
                bought.toBigDecimal()
            }
            val newOwnedAsset = AssetAmount(id, buySymbol, newBoughtBalance.toString())
            Log.d(TAG, "makeTransaction: inserting $newOwnedAsset")
            db.insertOwnedAsset(newOwnedAsset).let {
                // Debug log result
                val updatedAsset = db.getOwnedAsset(id, buySymbol).first()
                Log.d(TAG, "makeTransaction: Updated asset: " + updatedAsset)
                _amountOwned.value = updatedAsset.amountOwned
            }
        }
    }
}