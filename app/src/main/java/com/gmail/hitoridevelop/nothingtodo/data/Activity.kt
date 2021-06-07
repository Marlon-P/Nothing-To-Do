package com.gmail.hitoridevelop.nothingtodo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey @ColumnInfo(name = "id") val name: String,
    val type: String,
    val accessibilityRange: String,
    val participantRange: String,
    val priceRange: String

)
