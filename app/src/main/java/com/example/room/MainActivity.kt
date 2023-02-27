package com.example.room

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.room.databinding.ActivityMainBinding
import com.module.databasemanager.AppDatabase
import com.module.databasemanager.Memo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private lateinit var memoAdapter: MemoAdapter
//    private lateinit var db: _AppDatabase
//    private val viewModel: _MemoViewModel by viewModels()

    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //binding.mainActivity = this

        appDatabase = AppDatabase.getInstance(applicationContext)!!

//        try {
//            db = _AppDatabase.getInstance(this)!!
//            memoAdapter = MemoAdapter(this)
//            memoAdapter.memoDao = db.memoDao()
//            binding.recyclerView.adapter = memoAdapter
//            binding.recyclerView.layoutManager = LinearLayoutManager(this)
//
//            viewModel.getDbData(this)
//            viewModel.memoList.observe(this) {
//                memoAdapter.submitList(it)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

    fun onInsert(v: View) {
        Log.d("testLog", "onInsert: aaaaaa")

        CoroutineScope(Dispatchers.IO).launch {
            val memo = Memo(
                id =111,
                content =  "asdfasdf"
            )
            appDatabase.memoDao().insert(memo)
        }

//        if (binding.editMemo.text.isNotEmpty()) {
//            try {
//                viewModel.insertData(
//                    this,
//                    memo = _Memo(
//                        id = System.currentTimeMillis().apply {
//                            val sdf = SimpleDateFormat("yyMMddhhmm")
//                            sdf.format(this)
//                        },
//                        content = binding.editMemo.text.toString(),
//                        datetime = System.currentTimeMillis()
//                    ))
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
    }

    fun onGetData(v: View) {
        Log.d("testLog", "onInsert: bbbbb")

        CoroutineScope(Dispatchers.IO).launch {
//            val memo = Memo(111)
//            appDatabase.memoDao().insert(memo)
            appDatabase.memoDao().getAll().let {
                Log.d("testLog", "onGetData: $it")
            }

        }
    }

}