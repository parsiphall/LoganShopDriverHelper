package com.parsiphal.loganshopdriverhelper.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

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
    @ColumnInfo var moveTo: Int = 0,
    @ColumnInfo var commentSimple: String = ""
) : Serializable