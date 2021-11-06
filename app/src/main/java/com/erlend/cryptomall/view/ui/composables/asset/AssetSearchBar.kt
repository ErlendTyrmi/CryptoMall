/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.view.ui.composables.asset

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.erlend.cryptomall.view.viewModels.AssetViewModel


@ExperimentalComposeUiApi
@Composable
fun AssetSearchBar(assetViewModel: AssetViewModel) {
    // Receiving composable filters based on this
    val query = assetViewModel.getQuery()
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = query.value,
        onValueChange = { query.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        label = {
            Text(text = "Search", color = MaterialTheme.colors.onSurface)
        },
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        // Experimental, might break in Compose 1.0.5!
        keyboardActions = KeyboardActions(
            onSearch = {keyboardController?.hide()})
    )
}