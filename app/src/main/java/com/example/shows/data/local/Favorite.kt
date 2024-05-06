package com.example.shows.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "show")
data class Show(
    val name: String,
    @PrimaryKey
    val id: Int
)