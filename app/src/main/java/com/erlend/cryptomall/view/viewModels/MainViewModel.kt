/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.viewModels

import android.accounts.Account
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erlend.cryptomall.domain.model.entities.*
import com.erlend.cryptomall.repo.dto.AssetDtoListServerResponse
import com.erlend.cryptomall.repo.dto.AssetDtoServerResponse
import com.erlend.cryptomall.repo.dto.dtoConversion.toAsset
import com.erlend.cryptomall.repo.local.LocalDao
import com.erlend.cryptomall.repo.remote.CoinCapApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

// Main ViewModel handles global methods used for the account E.g. calculate owned assets sum value
@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: CoinCapApi,
    private val db: LocalDao
) : ViewModel() {
    private val TAG = "MainViewModel: "
    private var account: CryptoMallAccount? = null

    // Setup account on boot
    init {
        viewModelScope.launch {
            account = db.getAccount()
            if (account == null) {
                setupAccount()
            }
        }

    }

    suspend fun setupAccount() {
        // Create account
        db.insertAccount(CryptoMallAccount(UUID.randomUUID()))

        // Confirm and read
        account = db.getAccount()
        Log.d(TAG, "Account: " + account)

        val accountId = account?.accountId!!

        // Setup account with 10000 USD
        db.insertOwnedAsset(AssetAmount(accountId, "USD", "10000"))

        // Confirm
        Log.d(TAG, "setupAccount: " + db.getOwnedAssets(accountId))
    }

    fun getOwnedAssets() {}

    // "Points
    fun getPoints() {}
}





