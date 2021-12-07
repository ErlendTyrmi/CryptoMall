/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.asset

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.erlend.cryptomall.domain.model.entities.Asset

@Composable
fun AssetListCard(
    asset: Asset = Asset(),
    onClickAsset: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp)
            .clickable { onClickAsset(asset.symbol) }
    ) {
        Image(
            painter = rememberImagePainter(
                data = "https://static.coincap.io/assets/icons/" + asset.symbol.lowercase() + "@2x.png",
            ),
            contentDescription = asset.name + " logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
        Column(modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
            Row {
                Text(
                    text = asset.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)) {
                Text(
                    modifier = Modifier.weight(2F),
                    text = "Price in USD: " + asset.priceUsd.take(5),
                    fontSize = 14.sp,
                )
                Text(
                    modifier = Modifier.weight(2F),
                    text = "Change: " + asset.changePercent24Hr.take(5) + "%",
                    fontSize = 14.sp
                )
            }
        }
    }
    Divider(
        color = MaterialTheme.colors.secondary,
        thickness = 1.dp,
        modifier = Modifier.padding(16.dp),
    )

}
