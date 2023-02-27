package com.module.appdatabase

import androidx.room.*
import com.module.appdatabase.AppDatabase.Companion.DB_NAME

@Dao
interface MemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memo: Memo)

    @Query("SELECT * FROM $DB_NAME")
    fun getAll(): List<Memo>

    @Query("SELECT content FROM $DB_NAME WHERE id = :no")
    fun getMemo(no:Int): String

    @Update
    fun update(memo: Memo)

    @Delete
    fun delete(memo: Memo)
}