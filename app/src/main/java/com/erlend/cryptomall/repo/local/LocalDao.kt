/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.repo.local

import androidx.room.*
import com.erlend.cryptomall.model.entities.Asset
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {

    // Assets (cryptocurrency data)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAssets(vararg asset: Asset)

    @Query("SELECT * FROM asset ORDER BY changePercent24Hr DESC")
    fun getAssets(): Flow<List<Asset>>

    @Query("SELECT * FROM asset where symbol LIKE :symbol")
    fun getAsset(symbol: String): Flow<Asset>

    @Update
    fun updateAsset(asset: Asset)

    // Account

    /*@Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAccount(account: CryptoMallAccount)

    @Query("SELECT * FROM account LIMIT 1")
    fun getAccount(): CryptoMallAccount*/

}