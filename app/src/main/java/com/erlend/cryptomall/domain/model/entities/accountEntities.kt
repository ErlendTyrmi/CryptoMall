/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

// Account

@Entity(tableName = "account")
data class CryptoMallAccount(
    @PrimaryKey
    val accountId: UUID = UUID.randomUUID() // Only allow one account
)

// Balance
@Entity(tableName = "asset_amount", primaryKeys = ["accountId", "assetSymbol"])
data class AssetAmount(
    val accountId: UUID,
    val assetSymbol: String,
    val amountOwned: String
)

// Transactions
@Entity(tableName = "asset_transaction", primaryKeys = ["accountId", "timestamp"])
data class AssetTransaction(
    val accountId : UUID,
    val timestamp: Long,
    val inCurrencySymbol: String,
    val outCurrencySymbol: String,
    val inAmount: String,
    val outAmount: String,
)
