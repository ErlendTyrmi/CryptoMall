/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface

import com.erlend.cryptomall.view.ui.theme.CryptoMallTheme
import com.erlend.cryptomall.view.viewModels.AssetViewModel
import com.erlend.cryptomall.view.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val model: MainViewModel by viewModels()
    private val assetModel: AssetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Test api
        assetModel.pullAssetsRemote()
        assetModel.pullAssetRemote("ethereum")


        setContent {
            CryptoMallTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Navigation(assetModel)
                }
            }
        }
    }
}

