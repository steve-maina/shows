package com.example.shows.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "show", indices = [Index(value = ["show_id"],unique = true)])
data class Show(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "show_id")
    val showId: Int
)