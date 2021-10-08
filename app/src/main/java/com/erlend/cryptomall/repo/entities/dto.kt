package com.erlend.cryptomall.repo.entities

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

    // Asset data
    data class Data(
        val changePercent24Hr: String,
        val id: String,
        val marketCapUsd: String,
        val maxSupply: String,
        val name: String,
        val priceUsd: String,
        val rank: String,
        val supply: String,
        val symbol: String,
        val volumeUsd24Hr: String,
        val vwap24Hr: String
    )


