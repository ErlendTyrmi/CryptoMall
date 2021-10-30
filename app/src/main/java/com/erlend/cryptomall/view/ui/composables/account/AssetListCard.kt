/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.erlend.cryptomall.domain.model.entities.Asset

@Composable
fun AssetListCard(
    asset: Asset = Asset("_", "_", "_", "_", "_"),
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Image(
            painter = rememberImagePainter(
                //data = "https://static.coincap.io/assets/icons/btc@2x.png"
                data = "https://static.coincap.io/assets/icons/" + asset.symbol.lowercase() + "@2x.png",
            ),
            contentDescription = asset.name + " logo",
            modifier = Modifier.size(32.dp)
        )
        Column() {
            Row {

                Text(text = asset.name)
            }
            Row {
                Text(
                    text = "Price in USD: " + asset.priceUsd.take(5)
                )
                Text(
                    text = "Change: " + asset.changePercent24Hr.take(5) + "%"
                )
            }
            Divider(
                color = Color.DarkGray,
                thickness = 1.dp,
                modifier = Modifier.padding(0.dp, 8.dp)
            )
        }
    }
}
