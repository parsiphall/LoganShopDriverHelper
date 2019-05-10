package com.parsiphal.loganshopdriverhelper

import android.app.Application
import android.arch.persistence.room.Room
import com.parsiphal.loganshopdriverhelper.data.DataBase

const val DB_NAME = "helper_DB"

val DB: DataBase by lazy {
    MainApp.mDataBase!!
}

class MainApp: Application() {

    companion object{
        var mDataBase: DataBase? = null
    }

    override fun onCreate() {
        super.onCreate()
        mDataBase = Room
            .databaseBuilder(applicationContext,DataBase::class.java, DB_NAME)
            .build()
    }
}