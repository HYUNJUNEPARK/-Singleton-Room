package com.example.room.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Memo::class], version = 1, exportSchema = false)
abstract class MemoDatabase: RoomDatabase() {
    abstract fun memoDao(): MemoDao
}