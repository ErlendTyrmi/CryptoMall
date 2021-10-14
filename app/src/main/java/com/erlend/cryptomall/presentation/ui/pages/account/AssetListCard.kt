/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.presentation.ui.pages.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.erlend.cryptomall.domain.model.entities.Asset


@Composable
fun AssetListCard(asset: Asset){
    Column() {
        Row{
            Image(
                painter = rememberImagePainter(
                    data = "// Icons @ https://static.coincap.io/assets/icons/"+ asset.symbol + "@2x.png",
                ),
                contentDescription = asset.name + " logo",
                modifier = Modifier.size(128.dp)
            )
            Text(text = asset.name)
        }
        Row{
           Text(text = "Price in USD: " + asset.priceUsd + " Change: "+ asset.changePercent24Hr + "%")
        }
    }
}
