package com.parsiphal.loganshopdriverhelper.data

import android.arch.persistence.room.*
import android.arch.persistence.room.Dao

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDelivery(delivery: Delivery)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTotal(total: Total)

    @Query("SELECT * FROM Delivery")
    fun getAllDeliveries(): List<Delivery>

    @Query("SELECT * FROM Total")
    fun getAllTotals(): List<Total>

    @Query("SELECT * FROM Delivery WHERE deliveryDate LIKE :date")
    fun getDeliveriesByDate(date: String): List<Delivery>

    @Query("SELECT * FROM Delivery WHERE deliveryDate LIKE :month")
    fun getDeliveriesByMonth(month: String): List<Delivery>

    @Query("SELECT * FROM Total WHERE date LIKE :month")
    fun getTotalsByMonth(month: String): List<Total>

    @Delete
    fun deleteDelivery(delivery: Delivery)

    @Delete
    fun deleteTotal(total: Total)
}