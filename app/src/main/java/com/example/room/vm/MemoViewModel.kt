package com.example.room.vm

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.room.MemoAdapter
import com.example.room.db.AppDatabase
import com.example.room.db.Memo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoViewModel : ViewModel() {
    private val _memoList = MutableLiveData<List<Memo>>()
    val memoList: LiveData<List<Memo>>
        get() = _memoList

    fun getDbData(context: Context) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val db = AppDatabase.getInstance(context)
                db?.memoDao()?.getAll().let { dbMemoList ->
                    CoroutineScope(Dispatchers.Main).launch {
                        _memoList.value = dbMemoList
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}