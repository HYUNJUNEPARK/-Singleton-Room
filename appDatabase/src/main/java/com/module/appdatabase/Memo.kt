package com.module.appdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.module.appdatabase.AppDatabase.Companion.DB_NAME

@Entity(tableName = DB_NAME)
data class Memo(
    @PrimaryKey
    var id: Long,

    @ColumnInfo
    var content: String,

    @ColumnInfo(name = "date")
    var datetime: Long,
)
