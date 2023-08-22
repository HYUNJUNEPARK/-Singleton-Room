package com.example.room

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.room.adapter.MemoAdapter
import com.example.room.databinding.ActivityMainBinding
import com.example.room.db.AppDatabase
import com.example.room.db.Memo
import com.example.room.vm.MemoViewModel

class MainActivity : AppCompatActivity(), MemoAdapter.ClickEventListener {
    private lateinit var binding: ActivityMainBinding

    private val memoViewModel: MemoViewModel by viewModels {
        MemoViewModel.provideFactory(AppDatabase.getInstance(applicationContext))
    }

    private val memoAdapter: MemoAdapter by lazy {
        MemoAdapter(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this@MainActivity

        binding.recyclerView.adapter = memoAdapter

        memoViewModel.getAllItems()
        memoViewModel.memoList.observe(this) {
            memoAdapter.submitList(it)
        }
    }

    fun insertItem() {
        if (binding.editMemo.text.isNotEmpty()) {
            memoViewModel.addItem(
                memo = Memo(
                    id = System.currentTimeMillis(),
                    content = binding.editMemo.text.toString(),
                    dateTime = System.currentTimeMillis()
                )
            )
        }
    }

    override fun onModifyButtonLongClickEvent(item: Memo) {
        Toast.makeText(this, "onLongClickEvent", Toast.LENGTH_SHORT).show()
    }

    override fun onModifyButtonShortClickEvent(newItem: Memo) {
        memoViewModel.updateItem(newItem)
    }

    override fun onDeleteButtonShortClickEvent(item: Memo) {
        memoViewModel.deleteItem( item)
    }
}

