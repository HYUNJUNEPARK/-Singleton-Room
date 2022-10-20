package com.example.room

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.databinding.ActivityMainBinding
import com.example.room.db.AppDatabase
import com.example.room.db.Memo
import com.example.room.vm.MemoViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var memoAdapter: MemoAdapter
    private lateinit var db: AppDatabase
    private val viewModel: MemoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        try {
            db = AppDatabase.getInstance(this)!!
            memoAdapter = MemoAdapter()
            memoAdapter.memoDao = db.memoDao()
            binding.recyclerView.adapter = memoAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.mainActivity = this

            viewModel.getDbData(this)
            viewModel.memoList.observe(this) {
                memoAdapter.submitList(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onUpdate() {
        if (binding.editMemo.text.isNotEmpty()) {
            try {
                val memo = Memo(
                    idx = memoAdapter.currentList.size,
                    content = binding.editMemo.text.toString(),
                    datetime = System.currentTimeMillis()
                )
                viewModel.updateData(this, memo)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}