package com.parsiphal.loganshopdriverhelper.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Delivery::class, Total::class], version = 18)
abstract class DataBase : RoomDatabase() {
    abstract fun getDao(): Dao
}