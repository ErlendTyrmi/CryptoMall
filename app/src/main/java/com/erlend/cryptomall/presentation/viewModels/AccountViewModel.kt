/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.erlend.cryptomall.data.repo.local.LocalDao
import com.erlend.cryptomall.data.repo.remote.CoinCapApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val api: CoinCapApi,
    val db: LocalDao
) : ViewModel() {


}