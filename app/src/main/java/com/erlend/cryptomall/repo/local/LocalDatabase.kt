/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.repo.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.erlend.cryptomall.domain.model.entities.*

@Database(
    entities = [
        Asset::class,
        HistoryAsset::class,
        CryptoMallAccount::class,
        AssetAmount::class,
        AssetTransaction::class
    ],
    version = 1,
//    autoMigrations = [
//        AutoMigration (from = 2, to = 3)
//    ]
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun localDao(): LocalDao
}