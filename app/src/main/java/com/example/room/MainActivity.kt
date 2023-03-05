package com.example.room

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.databinding.ActivityMainBinding
import com.module.databasemanager.data.AppDatabase
import com.module.databasemanager.data.Memo
import com.module.databasemanager.ui.MemoViewModel
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity(), MemoAdapter.ClickEventListener {
    private lateinit var binding: ActivityMainBinding
    private val memoViewModel: MemoViewModel by viewModels()
    private lateinit var memoAdapter: MemoAdapter

    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        appDatabase = AppDatabase.getInstance(applicationContext)!!

        try {
            appDatabase = AppDatabase.getInstance(this)!!
            memoAdapter = MemoAdapter(this@MainActivity, this)
            memoAdapter.memoDao = appDatabase.memoDao()
            binding.recyclerView.adapter = memoAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            memoViewModel.getDbData(this)
            memoViewModel.memoList.observe(this) {
                memoAdapter.submitList(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onInsert(v: View) {
        if (binding.editMemo.text.isNotEmpty()) {
            try {
                memoViewModel.insertData(
                    context = applicationContext,
                    memo = Memo(
                        id = System.currentTimeMillis().apply {
                            val simpleDateFormat = SimpleDateFormat("yyMMddhhmm")
                            simpleDateFormat.format(this)
                        },
                        content = binding.editMemo.text.toString(),
                        datetime = System.currentTimeMillis()
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onLongClickEvent(item: Memo) {
        Log.d("testLog", "onLongClickEvent : $item")
    }

    override fun onShortClickEvent(item: Memo) {
        Log.d("testLog", "onShortClickEvent : $item")
    }
}

