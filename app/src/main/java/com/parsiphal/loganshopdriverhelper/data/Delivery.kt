package com.parsiphal.loganshopdriverhelper.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Delivery(
    @PrimaryKey(autoGenerate = true) @ColumnInfo var id: Long = 0,
    @ColumnInfo var deliveryDate: String = "",
    @ColumnInfo var workType: Int = 0,
    @ColumnInfo var deliveryType: Int = 0,
    @ColumnInfo var payType: Int = 0,
    @ColumnInfo var address: String = "",
    @ColumnInfo var cost: Int = 0,
    @ColumnInfo var comment: String = "",
    @ColumnInfo var expense: Int = 0,
    @ColumnInfo var expenseType: Int = 0,
    @ColumnInfo var ifSalary: Int = 0,
    @ColumnInfo var moveFrom: Int = 0,
    @ColumnInfo var moveTo: Int = 0
)