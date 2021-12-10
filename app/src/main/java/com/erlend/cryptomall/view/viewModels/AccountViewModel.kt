/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ActivityScenario.launch
import com.erlend.cryptomall.domain.model.entities.*
import com.erlend.cryptomall.repo.local.LocalDao
import com.erlend.cryptomall.repo.remote.CoinCapApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

// Main ViewModel handles global methods used for the account, e.g. calculate owned assets sum value
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val api: CoinCapApi,
    private val db: LocalDao
) : ViewModel() {
    private val TAG = "MainViewModel: "
    private val referenceAsset = "USD"
    private val referenceAssetName = "US Dollar"
    private var account: CryptoMallAccount? = null

    // Points = owned data in US dollars
    private val _points = MutableLiveData<String>()
    val points: LiveData<String>
        get() = _points

    // Account ID - Created on first boot
    private val _accountId = MutableLiveData<UUID>()
    val accountId: LiveData<UUID>
        get() = _accountId

    // Owned assets as list
    private val _ownedAssets = MutableLiveData<List<AssetAmount>>()
    val ownedAssets: MutableLiveData<List<AssetAmount>>
        get() = _ownedAssets

    // Owned assets as list
    private val _transactions = MutableLiveData<List<AssetTransaction>>()
    val transactions: MutableLiveData<List<AssetTransaction>>
        get() = _transactions

    // Setup account on boot
    init {
        viewModelScope.launch {

            account = db.getAccount()
            if (account != null) {
                _accountId.value = account!!.accountId
                updatePointsSum(account!!.accountId)
                Log.d(TAG, "getAccountOrCreate: Found account " + accountId.value)
            } else {
                val id = UUID.randomUUID() // Create random internal id
                _accountId.value = id
                setupAccount(id)
            }
        }

        // Also initiate points
        _points.value = "_"
    }

    private suspend fun setupAccount(id: UUID) {
        // Create account
        db.insertAccount(CryptoMallAccount(id))

        // Setup owned assets with 10000 USD
        val amount = BigDecimal(10000)
        db.insertOwnedAsset(AssetAmount(id, referenceAsset, amount.toString()))

        // Add first transaction
        db.insertTransaction(
            AssetTransaction(
                accountId = id,
                timestamp = System.currentTimeMillis(),
                inAmount = amount.toString(),
                inCurrencySymbol = referenceAsset,
                outAmount = "0",
                outCurrencySymbol = referenceAsset,
            )
        )

        // Update points for the new account
        updatePointsSum(id)
    }

    // Calculate points
    fun updatePointsSum(id: UUID) {
        viewModelScope.launch {
            var sum = BigDecimal(0)
            val assetAmounts = db.getOwnedAssets(id)

            // Add as livedata
            _ownedAssets.value = assetAmounts

            assetAmounts.forEach { assetAmount ->
                Log.d(TAG, "updatePointsSum: $assetAmount")
                if (assetAmount.assetSymbol == referenceAsset) {
                    // Update dollars
                    sum = sum.add(assetAmount.amountOwned.toBigDecimal())
                } else {
                    // Update other owned assets
                    val asset = db.getAsset(assetAmount.assetSymbol)
                    sum = sum.add(
                        asset.priceUsd.toBigDecimal()
                            .times(assetAmount.amountOwned.toBigDecimal())
                    )

                }
            }

            Log.d(TAG, "getPointsSum: $sum")
            _points.value = sum.toString().take(5)
        }
    }

    fun updateTransactions(){
        viewModelScope.launch {
        _transactions.value = accountId.value?.let { db.getTransactions(it) }
    }}
}







