/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.repo.dto.dtoConversion

import com.erlend.cryptomall.domain.model.entities.Asset
import com.erlend.cryptomall.domain.model.entities.HistoryAsset
import com.erlend.cryptomall.repo.dto.Data
import com.erlend.cryptomall.repo.dto.HistoryData

fun Data.toAsset() = Asset(
    changePercent24Hr = changePercent24Hr,
    id = id,
    name = name,
    priceUsd = priceUsd,
    symbol = symbol,
    rank = rank
)


fun HistoryData.toHistoryAsset() = HistoryAsset(
    date = date,
    priceUsd = priceUsd,
    time = time
)