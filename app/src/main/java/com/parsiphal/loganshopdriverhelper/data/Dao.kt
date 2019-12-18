package com.parsiphal.loganshopdriverhelper.data

import androidx.room.*
import androidx.room.Dao

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

    @Query("SELECT * FROM Total WHERE dayOrMonth LIKE 0")
    fun getAllDailyTotals(): List<Total>

    @Query("SELECT * FROM Total WHERE dayOrMonth LIKE 1")
    fun getAllMonthlyTotals(): List<Total>

    @Query("SELECT * FROM Delivery WHERE deliveryDate LIKE :date")
    fun getDeliveriesByDate(date: String): List<Delivery>

    @Query("SELECT * FROM Delivery WHERE deliveryDate LIKE :month")
    fun getDeliveriesByMonth(month: String): List<Delivery>

    @Query("SELECT * FROM Total WHERE date LIKE :month AND dayOrMonth LIKE 0")
    fun getTotalsByMonth(month: String): List<Total>

    @Query("SELECT * FROM Total WHERE date LIKE :year AND dayOrMonth LIKE 1")
    fun getMonthTotals(year: String): List<Total>

    @Update
    fun updateDelivery(delivery: Delivery)

    @Delete
    fun deleteDelivery(delivery: Delivery)

    @Delete
    fun deleteTotal(total: Total)
}