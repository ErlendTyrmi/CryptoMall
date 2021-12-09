/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.repo.local

import androidx.room.*
import com.erlend.cryptomall.domain.model.entities.Asset
import com.erlend.cryptomall.domain.model.entities.AssetAmount
import com.erlend.cryptomall.domain.model.entities.AssetTransaction
import com.erlend.cryptomall.domain.model.entities.CryptoMallAccount
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface LocalDao {

    // Account

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAccount(account: CryptoMallAccount)

    @Query("SELECT * FROM account LIMIT 1")
    suspend fun getAccount(): CryptoMallAccount?

    // Assets

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(asset: Asset)

    @Query("SELECT * FROM asset ORDER BY rank DESC")
    fun getAssets(): Flow<List<Asset>>

    @Query("SELECT * FROM asset WHERE name like :search OR symbol LIKE :search ORDER BY rank DESC")
    fun searchAssets(search: String): Flow<List<Asset>>

    @Query("SELECT * FROM asset where symbol LIKE :symbol")
    suspend fun getAsset(symbol: String): Asset

    // Asset Ownership

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOwnedAsset(assetAmount: AssetAmount)

    @Query("SELECT * FROM asset_amount WHERE assetSymbol LIKE :symbol AND accountId LIKE :accountId")
    suspend fun getOwnedAsset(accountId: UUID, symbol: String): List<AssetAmount>

    @Query("SELECT * FROM asset_amount WHERE accountId = :accountId")
    suspend fun getOwnedAssets(accountId: UUID): List<AssetAmount>

    @Delete
    suspend fun deleteOwnedAsset(assetAmount: AssetAmount)

    // Asset Transactions

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(AssetTransaction: AssetTransaction)

    @Query("SELECT * FROM asset_transaction WHERE accountId LIKE :accountId")
    fun getTransactions(accountId: UUID): Flow<List<AssetTransaction>>
}