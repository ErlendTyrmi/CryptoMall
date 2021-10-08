package com.erlend.cryptomall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.erlend.cryptomall.repo.CoinApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CoinApi
) : ViewModel() {



    private val users: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>().also {
            loadCurrencies()
        }
    }

    fun getCurrencies(): LiveData<List<String>> {
        return users
    }

    fun getCurrency(code: String): LiveData<List<String>> {
        return users
    }

    fun buyCurrency(code: String, amount: Double): LiveData<List<String>> {
        return users
    }

    fun sellCurrency(code: String, amount: Double): LiveData<List<String>> {
        return users
    }

    private fun loadCurrencies() {
        // Do an asynchronous operation to fetch all currencies.
    }

    private fun loadCurrency(code: String) {
        // Do an asynchronous operation to fetch single currency.
    }

    // Dollars to spend
    private fun loadLiquids() {
        // Do an asynchronous operation to read local liquids in dollars.
    }
}

