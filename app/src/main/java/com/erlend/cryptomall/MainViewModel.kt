package com.erlend.cryptomall

import android.util.Log
import androidx.lifecycle.ViewModel
import com.erlend.cryptomall.repo.remote.CoinCapApi
import com.erlend.cryptomall.repo.entities.Asset
import com.erlend.cryptomall.repo.entities.Assets
import com.erlend.cryptomall.repo.entities.Data
import com.erlend.cryptomall.repo.local.AssetDao
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import retrofit2.Callback
import java.lang.System.out

const val TAG = "MainViewModel: "

@HiltViewModel
class MainViewModel @Inject constructor(coinCapApi: CoinCapApi, assetDao: AssetDao) : ViewModel() {

    val api = coinCapApi
    val room = assetDao
    var assets: List<Data>? = null

    fun getAssets() {
        api.getAssets().enqueue(object : Callback<Assets> {
            override fun onResponse(call: Call<Assets>, response: Response<Assets>) {
                if (response.code() == 200) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        assets = responseBody.data
                        for (data in responseBody.data) {
                            Log.d(TAG, data.id)
                        }
                        //storeData(assets!!)
                    }
                }
            }

            override fun onFailure(call: Call<Assets>, t: Throwable) {
                Log.d(TAG, "Failed to retrieve data: " + t.localizedMessage)
            }
        })
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

    fun buyCurrency(code: String, amount: Double) {

    }

    fun sellCurrency(code: String, amount: Double) {

    }

    private fun loadCurrency(code: String) {
        // Do an asynchronous operation to fetch single currency.
    }

    // Dollars to spend
    private fun loadLiquids() {
        // Do an asynchronous operation to read local liquids in dollars.
    }

//    fun storeData(assets: List<Data>){
//        val array: Array<Data> = assets.toTypedArray()
//        room.insert(*array)
//    }
}

