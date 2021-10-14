/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.data.dto

import com.erlend.cryptomall.domain.model.entities.Asset
import com.erlend.cryptomall.domain.model.entities.Data
import com.erlend.cryptomall.domain.model.entities.HistoryAsset
import com.erlend.cryptomall.domain.model.entities.HistoryData

fun Data.toAsset() = Asset(
    changePercent24Hr = changePercent24Hr,
    id = id,
    name = name,
    priceUsd = priceUsd,
    symbol = symbol,
)


fun HistoryData.toHistoryAsset() = HistoryAsset(
    date = date,
    priceUsd = priceUsd,
    time = time
)