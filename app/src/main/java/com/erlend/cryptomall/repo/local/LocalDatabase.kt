/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.repo.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erlend.cryptomall.model.entities.Asset
import com.erlend.cryptomall.model.entities.CryptoMallAccount

@Database(
    entities = [
        Asset::class,
        CryptoMallAccount::class,
    ],
    version = 1,
    /*autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ]*/
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun localDao(): LocalDao
}