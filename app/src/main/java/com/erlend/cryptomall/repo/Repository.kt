package com.erlend.cryptomall.repo

import androidx.lifecycle.LiveData
import com.erlend.cryptomall.repo.entities.Asset
import com.erlend.cryptomall.repo.entities.Data
import com.erlend.cryptomall.repo.local.AssetDao
import javax.inject.Inject

class Repository @Inject constructor(private val assetDao: AssetDao) {
    // Use this class to handle room and remote API's

    fun getAssets(): LiveData<Data> {
        return assetDao.getAssets()
    }

}