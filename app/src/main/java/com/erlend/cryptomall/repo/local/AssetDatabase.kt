package com.erlend.cryptomall.repo.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.erlend.cryptomall.repo.entities.Asset
import com.erlend.cryptomall.repo.entities.Data

@Database(
    entities = [Data::class], version = 2
)
abstract class AssetDatabase : RoomDatabase() {

    abstract fun assetDao(): AssetDao
}