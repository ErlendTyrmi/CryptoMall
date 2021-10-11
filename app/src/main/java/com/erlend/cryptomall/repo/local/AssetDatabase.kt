package com.erlend.cryptomall.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erlend.cryptomall.repo.entities.Asset

@Database(entities = arrayOf(Asset::class), version = 1)
abstract class AssetDatabase: RoomDatabase() {

    abstract fun assetDao(): AssetDao
}