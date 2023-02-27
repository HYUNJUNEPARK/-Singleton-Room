package com.example.room

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private lateinit var memoAdapter: MemoAdapter
//    private lateinit var db: _AppDatabase
//    private val viewModel: _MemoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this

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
        if (binding.editMemo.text.isNotEmpty()) {
            Log.d("testLog", "onInsert: aaaaaa")
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




        }
    }
}