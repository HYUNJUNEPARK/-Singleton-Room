package com.example.room.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.room.db.DBProvider.COLUMN_DATE
import com.example.room.db.DBProvider.DB_NAME

/*
no | content | dateTime
1  | memo    | 2022/01/01 01:01
*/
@Entity(tableName = DB_NAME)
class Memo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var no: Long? = null

    @ColumnInfo
    var content: String = ""

    @ColumnInfo(name = COLUMN_DATE)
    var datetime: Long = 0

    constructor(no: Long?=null, content: String, datetime: Long) {
        this.no = no
        this.content = content
        this.datetime = datetime
    }
}