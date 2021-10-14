/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface

import com.erlend.cryptomall.presentation.ui.theme.CryptoMallTheme
import com.erlend.cryptomall.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Test api
        model.getAssets()
        model.getAsset("ethereum")


        setContent {
            CryptoMallTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Navigation()
                }
            }
        }
    }
}


