/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.asset

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.erlend.cryptomall.R
import com.erlend.cryptomall.view.viewModels.AccountViewModel

// This composable shows the net held assets in "points", equal to dollars.

@Composable
fun AccountTopBar(accountViewModel: AccountViewModel, navController: NavController) {

    val points = accountViewModel.points.observeAsState()
    val id = accountViewModel.accountId.observeAsState()

    id.value?.let {
        accountViewModel.updatePointsSum(it)
    }

    Surface(Modifier.clickable(
        onClick = {
            navController.navigate("portfolio")
        }
    )) {
        Row (modifier = Modifier.fillMaxWidth().padding(8.dp)){

            Box(modifier = Modifier.fillMaxWidth(0.15f)) {
                Image(
                    painterResource(id = R.drawable.applogo),
                    contentDescription = "logo",
                    modifier = Modifier.align(
                        Alignment.BottomCenter
                    ),
                )
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.8f),
            ) {
                Text(
                    text = "Your account",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,

                )
                Text(text = "Account: *${id.value.toString().takeLast(6)}")
                Text(text = "Points (USD): ${points.value}")
            }
        }
    }
}

