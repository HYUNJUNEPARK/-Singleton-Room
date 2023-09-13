package com.example.room

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.room.adapter.ListAdapterEx
import com.example.room.databinding.ActivityMainBinding
import com.example.room.db.AppDatabase
import com.example.room.db.Memo
import com.example.room.callback.ClickListener
import com.example.room.db.MemoUiDto
import com.example.room.vm.ViewModelEx

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModelEx: ViewModelEx by viewModels {
        ViewModelEx.provideFactory(AppDatabase.getInstance(applicationContext))
    }

    //리스트 어댑터 + 클릭 콜백
    private val listAdapterEx: ListAdapterEx by lazy {
        ListAdapterEx(itemClickListener = object : ClickListener.ClickEventListener{
            //수정 버튼 롱클릭
            override fun onModifyLongClicked(item: MemoUiDto) {
                Toast.makeText(this@MainActivity, "onLongClickEvent", Toast.LENGTH_SHORT).show()
            }
            //수정 버튼 숏클릭
            override fun onModifyShortClicked(newItem: Memo) {
                viewModelEx.updateItem(newItem)
            }
            override fun onDeleteClicked(item: Memo) {
                viewModelEx.deleteItem(item)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this@MainActivity

        binding.recyclerView.apply {
            adapter = listAdapterEx
            setHasFixedSize(true)
        }

        viewModelEx.memoList.observe(this@MainActivity) { localDataList ->
            Log.d(TAG, "==============================")
            Log.d(TAG, "DB에 저장된 데이터:\n$localDataList")

            var dataList: ArrayList<MemoUiDto> = arrayListOf()

            for (memo in localDataList) {
                dataList.add(MemoUiDto (
                    id = memo.id,
                    content = memo.content,
                    dateTime = memo.dateTime,
                ))
            }

            Log.d(TAG, "어댑터로 전달할 데이터:\n$dataList")
            Log.d(TAG, "==============================")

            listAdapterEx.submitList(dataList)
        }
    }

    //저장 버튼
    fun onSaveButtonClicked() {
        if (binding.editMemo.text.isNotEmpty()) {
            viewModelEx.addItem(
                memo = Memo(
                    id = System.currentTimeMillis(),
                    content = binding.editMemo.text.toString(),
                    dateTime = System.currentTimeMillis()
                )
            )
        }
    }

    companion object {
        const val TAG = "testLog"
    }
}

