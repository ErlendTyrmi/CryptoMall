package com.erlend.cryptomall.repo.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

// One Asset
data class Asset(
    val `data`: Data,
    val timestamp: Long
)

// Asset list
data class Assets(
    val `data`: List<Data>,
    val timestamp: Long
)

@Entity(tableName = "assets")
// Asset data
data class Data(
    val changePercent24Hr: String,
    val id: String,
    val marketCapUsd: String?,
    val maxSupply: String?,
    val name: String,
    val priceUsd: String,
    val rank: String,
    val supply: String,
    @PrimaryKey
    val symbol: String,
    val volumeUsd24Hr: String,
    val vwap24Hr: String
)

// History
data class AssetHistory(
    val `data`: List<Data>,
    val timestamp: Long
)

// Cast History data before use
data class HistoryData(
    val date: String,
    val priceUsd: String,
    val time: Long
)


