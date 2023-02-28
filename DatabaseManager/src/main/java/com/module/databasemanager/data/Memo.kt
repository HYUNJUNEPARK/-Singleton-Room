package com.module.databasemanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.module.databasemanager.data.AppDatabase.Companion.DB_NAME

@Entity(tableName = DB_NAME)
data class Memo(
    @PrimaryKey
    var id: Long,

    @ColumnInfo
    var content: String,

    @ColumnInfo(name = "date")
    var datetime: Long
)
