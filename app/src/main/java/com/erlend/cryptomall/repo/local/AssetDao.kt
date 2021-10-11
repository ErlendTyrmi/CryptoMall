package com.erlend.cryptomall.repo.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.erlend.cryptomall.repo.entities.Asset
import com.erlend.cryptomall.repo.entities.Data

@Dao
interface AssetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asset: Data)

    @Query("SELECT * FROM assets")
    fun getAssets(): LiveData<Asset>

    @Query("SELECT * FROM assets where symbol LIKE :symbol")
    fun getAsset(symbol: String): LiveData<Asset>

    @Update
    fun update(asset: Asset)
}