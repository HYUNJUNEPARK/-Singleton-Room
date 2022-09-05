package com.example.room.db

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

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
object DBProvider {
    const val DB_NAME = "my_db"
    const val COLUMN_DATE = "date"
    private var instance: MemoDatabase? = null
    private val MIGRATE_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            val alter = "ALTER TABLE $DB_NAME ADD COLUMN new_title text"
            database.execSQL(alter)
        }
    }

    @Synchronized
    fun getInstance(context: Context): MemoDatabase? {
        if (instance == null) {
            synchronized(MemoDatabase::class){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemoDatabase::class.java,
                    DB_NAME
                )
                .addMigrations(MIGRATE_1_2)
                .build()
            }
        }
        return instance
    }
}