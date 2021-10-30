/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.erlend.cryptomall.R
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavController) {
    LaunchedEffect(key1 = true){
        delay(800L)
        navController.navigate("overview")
    }
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painterResource(id = R.drawable.logobag), contentDescription = "logo", modifier = Modifier.align(
            Alignment.Center))
    }
}