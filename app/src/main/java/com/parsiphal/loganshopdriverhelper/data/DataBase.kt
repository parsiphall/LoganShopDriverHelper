package com.parsiphal.loganshopdriverhelper.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Shift::class, Delivery::class, Total::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun getDao(): Dao
}