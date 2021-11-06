/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.asset

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// This composable shows the net held assets in "points", equal to dollars.

@Composable
fun AccountTopBar(){
    Box (modifier = Modifier.padding(8.dp).fillMaxWidth(),){
        Text(text = "Top bar for account pages!", modifier = Modifier.align(Alignment.Center))
    }
}

