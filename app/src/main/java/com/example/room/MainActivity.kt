package com.example.room

import android.os.Bundle
import android.util.Log
import android.view.View
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
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        try {
            db = AppDatabase.getInstance(this)!!
            initAdapter()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initAdapter() {
        try {
            adapter = Adapter()
            adapter.memoDao = db.memoDao()
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this)

            //Room DB 전체 데이터 로드
            CoroutineScope(Dispatchers.IO).launch {
                db.memoDao().getAll().let { memoList ->
                    adapter.submitList(memoList)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateButtonClicked(v: View) {
        if (binding.editMemo.text.isNotEmpty()) {
            try {
                val memo = Memo(
                    null,
                    content = binding.editMemo.text.toString(),
                    datetime = System.currentTimeMillis()
                )

                CoroutineScope(Dispatchers.IO).launch {
                    //Room DB에 새로운 데이터 저장
                    db.memoDao().insert(memo)
                    //TODO MainActivity 에 DB 에서 불러온 메모리스트를 담을 수 있는 리스트를 만들어서 새로운 데이터가 추가될 때 만든 리스트에 추가한 후 submit
                    //adapter.currentList.add(memo)
                    //https://stackoverflow.com/questions/59441832/how-to-modify-list-in-recycler-view-using-list-adapter
//

                    withContext(Dispatchers.Main) {
                        binding.editMemo.setText("")
                        adapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            //adapter.notifyDataSetChanged()
        }
    }
}