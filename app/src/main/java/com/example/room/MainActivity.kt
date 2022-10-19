package com.example.room

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.databinding.ActivityMainBinding
import com.example.room.db.Memo
import com.example.room.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var db: AppDatabase
    private lateinit var memoAdapter: MemoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        try {
            db = AppDatabase.getInstance(this)!!
            memoAdapter = MemoAdapter()
            memoAdapter.memoDao = db.memoDao()
            binding.recyclerView.adapter = memoAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this)

            getDatabaseData(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getDatabaseData(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            db.memoDao().getAll().let { memoList ->
                if (memoList.isNullOrEmpty()) {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(context, "Empty DB", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }
                memoAdapter.submitList(memoList)
            }
        }
    }

    fun updateButtonClicked(v: View) {
        if (binding.editMemo.text.isNotEmpty()) {
            try {
                val memo = Memo(
                    idx = memoAdapter.currentList.size,
                    content = binding.editMemo.text.toString(),
                    datetime = System.currentTimeMillis()
                )
                //DB에 item 추가
                CoroutineScope(Dispatchers.IO).launch {
                    db.memoDao().insert(memo)
                }
                //currentList 갱신
                val memoList = memoAdapter.currentList.toMutableList().apply {
                    this.add(memo)
                }

                memoAdapter.submitList(memoList)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}