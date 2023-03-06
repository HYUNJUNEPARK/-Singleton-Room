package com.module.databasemanager.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.module.databasemanager.data.AppDatabase
import com.module.databasemanager.data.Memo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MemoViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private var appDatabase: AppDatabase? = null

    private val _memoList = MutableLiveData<List<Memo>>()
    val memoList: LiveData<List<Memo>>
        get() = _memoList

    init {
        if (appDatabase == null) {
            appDatabase = AppDatabase.getInstance(context)
        }
    }

    /**
     * DB 에 아이템을 저장한다.
     *
     * @param memo 저장하려는 아이템
     */
    fun addItem(memo: Memo) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                appDatabase!!.memoDao().insert(memo)

                //데이터 동기화
                getAllItems()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * DB 에 저장된 모든 데이터를 가져와, LiveData(memoList) 를 초기화한다.
     */
    fun getAllItems() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                appDatabase!!.memoDao().getAll().let { itemList ->
                    withContext(Dispatchers.Main) {
                        _memoList.value = itemList
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * DB 에 저장된 특정 아이템을 수정한다.
     *
     * @param newMemo 수정하려는 아이템
     */
    fun updateItem(newMemo: Memo) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                appDatabase!!.memoDao().update(newMemo)

                //데이터 동기화
                getAllItems()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * DB 에 저장된 특정 아이템을 삭제한다.
     *
     * @param memo 삭제하려는 아이템
     */
    fun deleteItem(memo: Memo) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                appDatabase!!.memoDao().delete(memo)

                //데이터 동기화
                getAllItems()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * DB 에 저장된 모든 아이템을 삭제한다.
     */
    fun deleteAllItems() {

    }
}