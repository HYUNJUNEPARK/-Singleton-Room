package com.example.room.db

import androidx.room.*
import com.example.room.db.DBProvider.DB_NAME

@Dao
interface MemoDao {
    /**
     * Sample Code
     * ```
     * val memo = Memo(
     *      null,
     *      "sample",
     *      System.currentTimeMillis()
     * )
     * CoroutineScope(Dispatchers.IO).launch {
     *      db.memoDao().insert(memo)
     * }
     * ```
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memo: Memo)

    /**
     * Sample Code
     *
     * ```
     * CoroutineScope(Dispatchers.IO).launch {
     *      db.memoDao().getAll()
     * }
     * ```
     */
    @Query("SELECT * FROM $DB_NAME")
    fun getAll(): List<Memo>

    /**
     * Sample Code
     * ```
     * CoroutineScope(Dispatchers.IO).launch {
     *      db.memoDao().getMemo(1)
     * }
     * ```
     */
    @Query("SELECT content FROM $DB_NAME WHERE `no` = :no")
    fun getMemo(no:Int): String

    /**
     * Sample Code
     *
     * ```
     * val memo = Memo(
     *      memo.no,
     *      "sample",
     *      System.currentTimeMillis()
     * )
     * CoroutineScope(Dispatchers.IO).launch {
     *      db.memoDao().update(memo)
     * }
     * ```
     */
    @Update
    fun update(memo: Memo)

    /**
     * Sample Code
     * ```
     * CoroutineScope(Dispatchers.IO).launch {
     *      db.memoDao().delete(memo)
     * }
     * ```
     */
    @Delete
    fun delete(memo: Memo)
}