package com.example.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [_Memo::class], version = 1, exportSchema = false)
abstract class _AppDatabase: RoomDatabase() {
    abstract fun memoDao(): _MemoDao

    companion object {
        const val DB_NAME = "mDB"
        private var instance: _AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): _AppDatabase? {
            if (instance == null) {
                synchronized(_AppDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        _AppDatabase::class.java,
                        DB_NAME
                    ).build()
                }
            }
            return instance
        }
    }
}