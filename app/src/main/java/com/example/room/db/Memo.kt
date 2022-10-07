package com.example.room.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.room.db.AppDatabase.Companion.COLUMN_DATE
import com.example.room.db.AppDatabase.Companion.DB_NAME

/*
no | content | dateTime
1  | memo    | 2022/01/01 01:01
*/
@Entity(tableName = DB_NAME)
data class Memo(
    @PrimaryKey(autoGenerate = true)
    var idx: Int? = null,

    @ColumnInfo
    var content: String = "",

    @ColumnInfo(name = COLUMN_DATE)
    var datetime: Long = 0,
)