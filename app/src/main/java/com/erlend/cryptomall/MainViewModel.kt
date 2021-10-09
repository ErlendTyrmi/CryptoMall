package com.erlend.cryptomall

import android.util.Log
import androidx.lifecycle.ViewModel
import com.erlend.cryptomall.repo.remote.CoinCapApi
import com.erlend.cryptomall.repo.entities.Asset
import com.erlend.cryptomall.repo.entities.Assets
import com.erlend.cryptomall.repo.remote.CoinCapStaticApi
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import retrofit2.Callback

const val TAG = "MainViewModel: "

@HiltViewModel
class MainViewModel @Inject constructor(coinCapApi: CoinCapApi, coinCapStaticApi: CoinCapStaticApi) : ViewModel() {

    val api = coinCapApi

    fun getAssets() {
        api.getAssets().enqueue(object : Callback<Assets> {
            override fun onResponse(call: Call<Assets>, response: Response<Assets>) {
                if (response.code() == 200) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        for (asset in responseBody.data){
                            Log.d(TAG, asset.id)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Assets>, t: Throwable) {

            }
        })
        // put in room
    }

    fun getAsset(id: String) {
        api.getAsset(id).enqueue(object : Callback<Asset> {
            override fun onResponse(call: Call<Asset>, response: Response<Asset>) {
                if (response.code() == 200) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                            Log.d(TAG, responseBody.data.id + ", price: " + responseBody.data.priceUsd)
                    }
                }
            }

            override fun onFailure(call: Call<Asset>, t: Throwable) {

            }
        })
        // put in room
    }

    fun buyCurrency(code: String, amount: Double){

    }

    fun sellCurrency(code: String, amount: Double) {

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

