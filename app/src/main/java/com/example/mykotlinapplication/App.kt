package com.example.mykotlinapplication

import android.app.Application
import com.example.mykotlinapplication.database.AppDatabase
import com.example.mykotlinapplication.database.DataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DataBase.db = AppDatabase.getInstance(applicationContext)
    }

}

