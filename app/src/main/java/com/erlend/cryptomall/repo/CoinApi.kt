package com.erlend.cryptomall.repo

import com.erlend.cryptomall.repo.entities.Assets
import com.erlend.cryptomall.repo.entities.Data
import javax.inject.Inject


class CoinApi @Inject constructor(){

    // Authorization=Bearer XXXX

    // GET assets: api.coincap.io/v2/assets
    fun getAssets(): Assets {
        val assets = Assets(
            data = listOf<Data>(
                Data(
                    "", "", "", "", "",
                    "", "", "", "", "", ""
                )
            ), 1000L
        )
        return assets
    }

// GET/assets/{{id}}: api.coincap.io/v2/assets/bitcoin

}

