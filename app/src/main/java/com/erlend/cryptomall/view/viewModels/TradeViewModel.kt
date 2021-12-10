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
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
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
    private val _dollarsOwned = MutableLiveData<String>()
    val dollarsOwned: LiveData<String>
        get() = _dollarsOwned

    fun updateAssetLocal(symbol: String) {
        // Get assets from local, observe as LiveData
        viewModelScope.launch {
            asset.value = db.getAsset(symbol)
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

    fun updateOwnedAmount(){
        viewModelScope.launch {
            asset.value?.let {
                _amountOwned.value = pullOwnedAmount(it.symbol)?.amountOwned
                if (amountOwned.value == null || amountOwned.value == "null"){_amountOwned.value = "0"}
                Log.d(TAG, "updateOwnedAmount: amountOwned is now ${amountOwned.value} ")
            }
        }
    }

    // Get owned amount of an asset from the database
    private suspend fun pullOwnedAmount(symbol: String): AssetAmount? {
        val id = db.getAccount()!!.accountId
        var owned: AssetAmount? = null
        val ownedAsList = db.getOwnedAsset(id, symbol)
        if (ownedAsList.isNotEmpty()) {
            owned = ownedAsList.first()
        }
        Log.d(TAG, "getOwnedAmount: read from db: $owned")
        return owned
    }

    fun updateDollarsOwned(){
        viewModelScope.launch {
            _dollarsOwned.value = pullOwnedAmount(referenceAsset)?.amountOwned
        }
    }

    fun checkDollarsOwned(amount: String): Boolean {
        // False if nothing entered
        if (amount.isEmpty()) return false

        // Else if can't parse string
        try{
            amount.toBigDecimal()
        } catch (e: Exception){
            Log.d(TAG, "checkDraft: Cannot convert amount: '$amount' to BigDecimal")
            return false
        }

        // Check draft
        val price = asset.value?.priceUsd
        return (
                dollarsOwned.value != null
                        && price != null
                        && dollarsOwned.value!!.toBigDecimal()
                .compareTo(amount.toBigDecimal().times(price.toBigDecimal())) > -1 // If enough cash to buy
        )
    }

    fun getCurrentTotalPrice(amount: String): String {
        val sum = amount.toBigDecimal().times(asset.value!!.priceUsd.toBigDecimal())
        val rounded = sum.setScale(5, BigDecimal.ROUND_HALF_UP)
        return rounded.toPlainString()
    }

    fun buy(buySymbol: String, amount: String){
        // Calculate price
        val price = (asset.value?.priceUsd?.toBigDecimal()?.times(amount.toBigDecimal()))
        viewModelScope.launch {
            // Check draft, make Transaction
            if (checkDollarsOwned(amount))
                makeTransaction(buySymbol, referenceAsset, amount, price.toString())
        }
    }

    fun sell(sellSymbol: String, amount: String) {
        if (asset.value != null){
            val assetCopy = asset.value!!

            // Calculate price
            val price = assetCopy.priceUsd.toBigDecimal()
                .times(amount.toBigDecimal())

            // Update owned amount
            updateOwnedAmount()

            // Check draft, make Transaction
            viewModelScope.launch {
                val owned = amountOwned.value?.toBigDecimal() ?: BigDecimal("0")
                Log.d(TAG, "sell: Before transaction, owned is $owned, amount is $amount, price is $price. ")
                if (owned >= amount.toBigDecimal()){
                    makeTransaction(
                        buySymbol = referenceAsset, // buying dollars
                        sellSymbol = sellSymbol,
                        bought = price.toPlainString(), // in dollars
                        sold = amount) // in currency
             }
            }
        }
    }

    // Selling is buying dollars :-)
    private fun makeTransaction(
        buySymbol: String,
        sellSymbol: String,
        bought: String,
        sold: String
    ) {
        viewModelScope.launch {
            val id: UUID = db.getAccount()!!.accountId

            // Pay
            val soldAsset = pullOwnedAmount(sellSymbol)
                ?: throw Exception("makeTransaction Could not find owned $sellSymbol to sell or pay.")

            val newPaidBalance = soldAsset.amountOwned.toBigDecimal() - sold.toBigDecimal()
            db.deleteOwnedAsset(AssetAmount(id, sellSymbol, soldAsset.amountOwned))

            // Post with updated amount
            val updatedPaidAsset = AssetAmount(id, sellSymbol, newPaidBalance.toString())
            db.insertOwnedAsset(updatedPaidAsset).let {
                // Check if transaction was successful
                val updatedAsset = db.getOwnedAsset(id, sellSymbol)
                Log.d(TAG, "makeTransaction: After payment sold asset is $updatedAsset")
                if (updatedPaidAsset != updatedAsset.first()) {
                    throw Exception("Failed to update sold asset!")
                }
            }

            // Update bought asset
            lateinit var newBoughtBalance: BigDecimal
            val boughtAsset = pullOwnedAmount(buySymbol)

            // Set the new balance for bought asset
            newBoughtBalance = if (boughtAsset != null &&
                boughtAsset.amountOwned.toBigDecimal()
                    .compareTo(BigDecimal(0)) == 1 // If more than zero
            ) {
                boughtAsset.amountOwned.toBigDecimal() + bought.toBigDecimal()
            } else {
                bought.toBigDecimal()
            }

            val newBoughtAsset = AssetAmount(id, buySymbol, newBoughtBalance.toString())
            Log.d(TAG, "makeTransaction: inserting $newBoughtAsset")
            db.insertOwnedAsset(newBoughtAsset).let {
                // Debug log result
                val updatedAsset = db.getOwnedAsset(id, buySymbol).first()
                Log.d(TAG, "makeTransaction: Updated asset: $updatedAsset")
                _amountOwned.value = updatedAsset.amountOwned
            }

            // Store the transaction (For history. Not changing account balance)
            db.insertTransaction(
                AssetTransaction(
                    accountId = id,
                    timestamp = System.currentTimeMillis(),
                    inAmount = bought,
                    inCurrencySymbol = buySymbol,
                    outAmount = sold,
                    outCurrencySymbol = sellSymbol,
                )
            )
            updateOwnedAmount()
            Log.d(TAG, "makeTransaction: paying $sold to buy $bought coins of $buySymbol")
        }
    }
}