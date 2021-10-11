package com.erlend.cryptomall.repo.local


import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestAssetDao {
    private lateinit var database : AssetDatabase
    @Before
    fun initDb(){
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AssetDatabase::class.java)
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