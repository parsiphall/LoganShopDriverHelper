package com.parsiphal.loganshopdriverhelper.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDelivery(delivery: Delivery)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTotal(total: Total)

    @Delete
    fun deleteDelivery(delivery: Delivery)

    @Delete
    fun deleteTotal(total: Total)
}