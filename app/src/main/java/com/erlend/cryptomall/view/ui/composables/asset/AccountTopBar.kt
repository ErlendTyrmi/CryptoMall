/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.asset

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.erlend.cryptomall.R
import com.erlend.cryptomall.view.viewModels.AccountViewModel

// This composable shows the net held assets in "points", equal to dollars.

@Composable
fun AccountTopBar(accountViewModel: AccountViewModel) {

    val points = accountViewModel.points.observeAsState()
    val id = accountViewModel.accountId.observeAsState()
    Surface() {
        Row (modifier = Modifier.fillMaxWidth().padding(8.dp)){

            Box(modifier = Modifier.fillMaxWidth(0.15f)) {
                Image(
                    painterResource(id = R.drawable.applogo),
                    contentDescription = "logo",
                    modifier = Modifier.align(
                        Alignment.BottomCenter
                    )
                )
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.8f),
            ) {
                Text(text = "Account: *${id.value.toString().takeLast(6)}")
                Text(text = "Points (USD): ${points.value}")
            }
        }
    }
}

