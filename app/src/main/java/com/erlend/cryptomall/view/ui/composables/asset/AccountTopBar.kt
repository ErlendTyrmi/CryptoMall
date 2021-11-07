/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.asset

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erlend.cryptomall.view.viewModels.AccountViewModel

// This composable shows the net held assets in "points", equal to dollars.

@Composable
fun AccountTopBar(accountViewModel: AccountViewModel) {

    val points = accountViewModel.points.observeAsState()

    Box (modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),){
        //Text(text = "User $points",  modifier = Modifier.align(Alignment.Center))
        Text(text = "Top Bar Account")
    }
}

