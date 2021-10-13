/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.repo.local


import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestLocalDao {
    private lateinit var database : LocalDatabase
    @Before
    fun initDb(){
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            LocalDatabase::class.java)
        .build()

    }

    @Test
    fun getAssets(){

    }

    @After
    fun closeDb(){
        database.close()
    }
}