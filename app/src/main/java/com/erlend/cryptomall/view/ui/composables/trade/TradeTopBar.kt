/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.trade

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.erlend.cryptomall.domain.model.entities.Asset

@Composable
fun TradeTopBar(asset: Asset?) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
    ) {
        asset?.let {
            Image(
                painter = rememberImagePainter(
                    //data = "https://static.coincap.io/assets/icons/btc@2x.png"
                    data = "https://static.coincap.io/assets/icons/" + asset.symbol.lowercase() + "@2x.png",
                ),
                contentDescription = asset.name + " logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp, 16.dp, 0.dp, 16.dp)
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = it.name + "(" + it.symbol + ")",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding( 0.dp, 8.dp, 0.dp, 0.dp),
                    text = "Price: " + it.priceUsd.take(7)
                )
            }
        }
    }
}