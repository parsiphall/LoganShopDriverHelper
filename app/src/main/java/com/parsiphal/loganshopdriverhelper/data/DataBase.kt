package com.parsiphal.loganshopdriverhelper.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Delivery::class, Total::class], version = 2)
abstract class DataBase : RoomDatabase() {
    abstract fun getDao(): Dao
}