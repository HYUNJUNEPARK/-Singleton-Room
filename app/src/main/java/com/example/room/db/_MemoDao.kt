package com.example.room.db

import androidx.room.*
import com.example.room.db._AppDatabase.Companion.DB_NAME

@Dao
interface _MemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memo: _Memo)

    @Query("SELECT * FROM $DB_NAME")
    fun getAll(): List<_Memo>

    @Query("SELECT content FROM $DB_NAME WHERE id = :no")
    fun getMemo(no:Int): String

    @Update
    fun update(memo: _Memo)

    @Delete
    fun delete(memo: _Memo)
}