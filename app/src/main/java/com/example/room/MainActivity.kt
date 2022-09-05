package com.example.room

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.databinding.ActivityMainBinding
import com.example.room.db.DBProvider
import com.example.room.db.Memo
import com.example.room.db.MemoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var db: MemoDatabase
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        try {
            db = DBProvider.getInstance(this)!!
            initAdapter()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initAdapter() {
        adapter = Adapter()
        adapter.memoDao = db.memoDao()

        CoroutineScope(Dispatchers.IO).launch {
            adapter.memoList.addAll(db.memoDao().getAll())
        }

        binding.recyclerMemo.adapter = adapter
        binding.recyclerMemo.layoutManager = LinearLayoutManager(this)
    }

    fun updateButtonClicked(v: View) {
        if (binding.editMemo.text.isNotEmpty()) {


            val memo = Memo(
                null,
                content = binding.editMemo.text.toString(),
                datetime = System.currentTimeMillis()
            )

            CoroutineScope(Dispatchers.IO).launch {
                db.memoDao().insert(memo)
                adapter.memoList.clear()
                adapter.memoList.addAll(db.memoDao().getAll())
            }

            adapter.notifyDataSetChanged()
            binding.editMemo.setText("")
        }
    }
}