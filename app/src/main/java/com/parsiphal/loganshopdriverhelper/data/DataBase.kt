package com.parsiphal.loganshopdriverhelper.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Delivery::class, Total::class], version = 5)
abstract class DataBase : RoomDatabase() {
    abstract fun getDao(): Dao
}