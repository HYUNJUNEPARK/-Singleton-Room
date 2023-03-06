package com.module.databasemanager.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.module.databasemanager.data.AppDatabase
import com.module.databasemanager.data.Memo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MemoViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    private val _memoList = MutableLiveData<List<Memo>>()
    val memoList: LiveData<List<Memo>>
        get() = _memoList

    /**
     * DB 에 아이템을 저장한다.
     *
     * @param memo 저장하려는 아이템
     */
    fun insertData(memo: Memo) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val db = AppDatabase.getInstance(context)
                db!!.memoDao().insert(memo)
            }
            val newMemoList = _memoList.value!!.toMutableList().apply {
                this.add(memo)
            }
            _memoList.value = newMemoList
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * DB 에 저장된 모든 데이터를 가져와, LiveData(memoList) 를 초기화한다.
     */
    fun getDbData() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val db = AppDatabase.getInstance(context)
                db!!.memoDao().getAll().let { dbDataList ->
                    withContext(Dispatchers.Main) {
                        _memoList.value = dbDataList
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * DB 에 저장된 특정 아이템을 수정한다.
     *
     * @param newMemo 수정하려는 아이템
     * @param idx 수정하려는 아이템의 인덱스
     */
    fun updateData(newMemo: Memo) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val db = AppDatabase.getInstance(context)
                db!!.memoDao().update(newMemo)
            }
//            val newMemoList = _memoList.value!!.toMutableList().apply {
//                this.removeAt(idx)
//                this.add(idx, newMemo)
//            }
//            _memoList.value = newMemoList
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * DB 에 저장된 특정 아이템을 삭제한다.
     *
     * @param memo 삭제하려는 아이템
     */
    fun deleteData(memo: Memo) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val db = AppDatabase.getInstance(context)
                db!!.memoDao().delete(memo)
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