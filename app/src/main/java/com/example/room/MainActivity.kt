package com.example.room

import android.os.Bundle
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

class MainActivity : AppCompatActivity() {
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
            memoAdapter = MemoAdapter(this)
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
                            val sdf = SimpleDateFormat("yyMMddhhmm")
                            sdf.format(this)
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
}

