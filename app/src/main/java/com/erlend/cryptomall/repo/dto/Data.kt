/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// This class hold the necessary data classes to receive data from CoinCap API

// One Asset response
data class AssetDtoServerResponse(
    val data: Data,
    val timestamp: Long
)

// Asset list response
data class AssetDtoListServerResponse(
    val `data`: List<Data>,
    val timestamp: Long
)

@Entity
// Asset named "data" has .toAsset() function
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

// History response
data class RemoteAssetHistory(
    val `data`: List<Data>,
    val timestamp: Long
)

@Entity
// History data (for drawing graphs)
data class HistoryData(
    val date: String,
    val priceUsd: String,
    @PrimaryKey
    val time: Long
)




