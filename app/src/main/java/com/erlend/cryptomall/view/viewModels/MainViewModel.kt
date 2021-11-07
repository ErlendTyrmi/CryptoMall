/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.viewModels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erlend.cryptomall.domain.model.entities.*
import com.erlend.cryptomall.repo.local.LocalDao
import com.erlend.cryptomall.repo.remote.CoinCapApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import java.lang.Exception
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*
import javax.inject.Inject

// Main ViewModel handles global methods used for the account, e.g. calculate owned assets sum value
@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: CoinCapApi,
    private val db: LocalDao
) : ViewModel() {
    private val TAG = "MainViewModel: "
    private var account: CryptoMallAccount? = null

    // Points = owned data in US dollars
    private val _points = MutableLiveData<Double>()
    val points: LiveData<Double>
        get() = _points

    // Setup account on boot
    init {
        viewModelScope.launch {
            account = db.getAccount()
            if (account == null) {
                setupAccount()
            }
        }
        _points.value = 0.0
    }

    suspend fun setupAccount() {
        // Create account
        db.insertAccount(CryptoMallAccount(UUID.randomUUID()))

        // Confirm and read
        account = db.getAccount()
        Log.d(TAG, "Account: $account")

        val accountId = account?.accountId!!

        // Setup account with 10000 USD
        val amount = BigDecimal(10000)
        val symbol = "USD"
        db.insertOwnedAsset(AssetAmount(accountId, symbol, amount.toString()))

        // Confirm
        Log.d(TAG, "setupAccount: " + db.getOwnedAssets(accountId))

        // Add first transaction
        db.insertTransaction(
            AssetTransaction = AssetTransaction(
                accountId = accountId,
                timestamp = System.currentTimeMillis(),
                amountInUsd = amount.toString(),
                amountInCurrency = amount.toString(),
                assetName = "",
                assetSymbol = symbol
            )
        )

        // Check Transaction
        db.getTransactions(accountId).collect { transactions ->
            val size: Int = transactions.size
            if (size != 1) {
                throw Exception(
                    "setupAccount: Failed. Transactions was not " +
                            "of length 1 at account setup. Found " + size + " transactions."
                )
            }  else {
                Log.d(TAG, "setupAccount: Right number of transactions")
            }
        }
    }

    // TODO: Think throught the flow.. will this always be fresh?
    suspend fun getPointsSum(accountId: UUID): BigDecimal {
        var sum = BigDecimal(0)
        db.getOwnedAssets(accountId).forEach { assetAmount ->
            db.getAsset(assetAmount.assetSymbol).collect { asset ->
                sum = sum.add(
                    asset.priceUsd.toBigDecimal()
                        .times(assetAmount.amountOwned.toBigDecimal())
                )
            }
        }
        Log.d(TAG, "getPointsSum: $sum")
        return sum
    }
}





