package com.example.room.vm

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.room.db.AppDatabase
import com.example.room.db.Memo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoViewModel : ViewModel() {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val _memoList = MutableLiveData<List<Memo>>()
    val memoList: LiveData<List<Memo>>
        get() = _memoList

    fun getDbData(context: Context) {
        try {
            ioScope.launch {
                val db = AppDatabase.getInstance(context)
                db!!.memoDao().getAll().let { dbDataList ->
                    mainScope.launch {
                        _memoList.value = dbDataList
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun insertData(context: Context, memo: Memo) {
        try {
            ioScope.launch {
                val db = AppDatabase.getInstance(context)
                db!!.memoDao().insert(memo)
            }
            val newMemoList = _memoList.value!!.toMutableList().apply {
                this!!.add(memo)
            }
            _memoList.value = newMemoList
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //modify
    @RequiresApi(Build.VERSION_CODES.N)
    fun updateData(context: Context, memo: Memo) {
        try {
            ioScope.launch {
                val db = AppDatabase.getInstance(context)
                db!!.memoDao().update(memo)
            }

            //TODO 삭제할 아이템의 인덱스를 알아 낸 뒤 1,2 실행
            //TODO idx 가 어려울 경우 다른 교체방법이 있는지 알아봐야함
            //TODO MemoAdapter 54-
            val newMemoList = _memoList.value!!.toMutableList().apply {

                //1 remove Item


                //2 add Item


            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //delete
    fun deleteData(context: Context, memo: Memo) {
        try {
            ioScope.launch {
                val db = AppDatabase.getInstance(context)
                db!!.memoDao().update(memo)
            }
            val newMemoList = _memoList.value!!.toMutableList().apply {
                this.remove(memo)
            }
            _memoList.value = newMemoList
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}