/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// Account

@Entity(tableName = "account")
data class CryptoMallAccount(
    @PrimaryKey
    val accountId: Int = 0, // Only allow one account
)

// Balance
@Entity(tableName = "asset_amount", primaryKeys = ["accountId", "assetSymbol"])
data class AssetAmount(
    val accountId: Int,
    val assetSymbol: String,
    val amountOwned: String
)

// Transactions
@Entity(tableName = "asset_transaction", primaryKeys = ["accountId", "timestamp"])
data class AssetTransaction(
    val accountId : String,
    val timestamp: Long,
    val amountInUsd: Double,
    val amountInCurrency: Double,
    val assetName: String,
    val assetSymbol: String,
)
