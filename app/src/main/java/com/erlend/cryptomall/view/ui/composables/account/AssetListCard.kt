/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.erlend.cryptomall.domain.model.entities.Asset

@Composable
fun AssetListCard(
    asset: Asset = Asset("_", "_", "_", "_", "_", "_"),
    onClickAsset: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClickAsset(asset.symbol) }
    ) {
        Image(
            painter = rememberImagePainter(
                //data = "https://static.coincap.io/assets/icons/btc@2x.png"
                data = "https://static.coincap.io/assets/icons/" + asset.symbol.lowercase() + "@2x.png",
            ),
            contentDescription = asset.name + " logo",
            modifier = Modifier
                .size(32.dp)
                .padding(0.dp, 0.dp, 8.dp, 0.dp)
        )
        Column() {
            Row {
                Text(
                    text = asset.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row {
                Text(
                    text = "Price in USD: " + asset.priceUsd.take(5),
                    fontSize = 14.sp,
                )
                Text(
                    text = "Change: " + asset.changePercent24Hr.take(5) + "%",
                    fontSize = 14.sp
                )
            }
        }
    }
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier.padding(8.dp)
    )
}
