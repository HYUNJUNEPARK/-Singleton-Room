package com.example.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * #Build :
 * ```
 * plugins {
 *    id 'kotlin-kapt'
 * }
 *
 * dependencies {
 *   //Room
 *   def roomVersion = "2.4.2"
 *   implementation("androidx.room:room-runtime:$roomVersion")
 *   kapt("androidx.room:room-compiler:$roomVersion")
 *
 *   //Coroutine
 *   implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9'
 *   implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'
 * }
 *```
 *
 * #Initialize
 * ```
 * private lateinit var db: MemoDatabase
 * //...
 * db = DBProvider.getInstance(this)!!
 * ```
 */
@Database(entities = [Memo::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun memoDao(): MemoDao

    companion object {
        const val DB_NAME = "myBb"
        const val COLUMN_DATE = "date"
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DB_NAME
                    )
                        .build()
                }
            }
            return instance
        }
    }
}