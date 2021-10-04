package com.erlend.cryptomall.ui.pages.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun AccountTopBar(){
    Box {
        Text(text = "Top bar for account pages!", modifier = Modifier.align(Alignment.Center))
    }
}

