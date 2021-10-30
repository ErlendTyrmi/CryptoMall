/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall

import android.app.Application
import android.content.res.Configuration
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.CoilUtils
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
class CryptoMallApp : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(applicationContext)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(applicationContext))
                    .build()
            }
            .build()
    }
}
