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
    val changePercent24Hr: String = "_",
    val id: String  = "_",
    val name: String  = "_",
    val priceUsd: String  = "_",
    @PrimaryKey
    val symbol: String  = "_",
    val rank: String  = "_",
)

@Entity(tableName = "history_asset")
data class HistoryAsset(
    val date: String  = "_",
    val priceUsd: String  = "_",
    @PrimaryKey()
    val time: Long = 0L
)