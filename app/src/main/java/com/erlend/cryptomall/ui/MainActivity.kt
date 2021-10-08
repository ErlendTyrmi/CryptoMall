package com.erlend.cryptomall.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.Observer
import com.erlend.cryptomall.MainViewModel
import com.erlend.cryptomall.ui.theme.CryptoMallTheme

class MainActivity : ComponentActivity() {
    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Example:
        //model.getCurrencies().observe(this, Observer<List<String>>{ users ->
           // update UI
        //})

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


