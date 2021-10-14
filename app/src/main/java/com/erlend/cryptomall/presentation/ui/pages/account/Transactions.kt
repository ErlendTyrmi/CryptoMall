/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.presentation.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.erlend.cryptomall.presentation.ui.pages.account.AccountTopBar

// Show all transactions made, date bought for $/ sold for $,
// Subpage of overview

@Composable
fun Transactions(){
    Column {
        AccountTopBar()
        Box(modifier = Modifier.fillMaxSize()){
            Text(text = "Transactions here!", modifier = Modifier.align(Alignment.Center))
        }
    }
}