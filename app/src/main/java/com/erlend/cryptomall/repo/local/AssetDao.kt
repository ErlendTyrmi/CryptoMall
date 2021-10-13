package com.erlend.cryptomall.repo.local

import androidx.room.*
import com.erlend.cryptomall.repo.entities.Data
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg asset: Data)

    @Query("SELECT * FROM assets ORDER BY changePercent24Hr DESC")
    fun getAssets(): Flow<List<Data>>

    @Query("SELECT * FROM assets where symbol LIKE :symbol")
    fun getAsset(symbol: String): Flow<Data>

    @Update
    fun update(data: Data)
}