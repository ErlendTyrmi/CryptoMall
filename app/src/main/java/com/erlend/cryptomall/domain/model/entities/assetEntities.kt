/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// Consider: @ColumnInfo(name = "like_this")
// Needs test env first...

@Entity(tableName = "asset")
data class Asset(
    val changePercent24Hr: String,
    val id: String,
    val name: String,
    val priceUsd: String,
    @PrimaryKey
    val symbol: String,
    val rank: String,
)

@Entity(tableName = "history_asset")
data class HistoryAsset(
    val date: String,
    val priceUsd: String,
    @PrimaryKey()
    val time: Long
)