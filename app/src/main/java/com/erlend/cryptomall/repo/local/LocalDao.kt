/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.repo.local

import androidx.room.*
import com.erlend.cryptomall.model.entities.Asset
import com.erlend.cryptomall.model.entities.AssetAmount
import com.erlend.cryptomall.model.entities.AssetTransaction
import com.erlend.cryptomall.model.entities.CryptoMallAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {

    // Account

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAccount(account: CryptoMallAccount)

    @Query("SELECT * FROM account LIMIT 1")
    fun getAccount(): CryptoMallAccount

    // Assets

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAssets(asset: Asset)

    @Query("SELECT * FROM asset ORDER BY changePercent24Hr DESC")
    fun getAssets(): Flow<List<Asset>>

    @Query("SELECT * FROM asset where symbol LIKE :symbol")
    fun getAsset(symbol: String): Flow<Asset>

    // Asset Ownership

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOwnedAsset(assetAmount: AssetAmount)

    @Query("SELECT * FROM asset_amount INNER JOIN asset ON assetSymbol = symbol & symbol LIKE :symbol WHERE accountId LIKE :accountId")
    fun getOwnedAsset(accountId: String, symbol: String): Flow<List<AssetAmount>>

    @Query("SELECT * FROM asset_amount INNER JOIN asset ON assetSymbol = symbol WHERE accountId LIKE :accountId")
    fun getOwnedAssets(accountId: String): Flow<List<AssetAmount>>

    @Delete
    fun deleteOwnedAsset(assetAmount: AssetAmount)

    // Asset Transactions

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(AssetTransaction: AssetTransaction)

    @Query("SELECT * FROM asset_transaction WHERE accountId LIKE :accountId")
    fun getTransactions(accountId: String): Flow<List<AssetTransaction>>
}