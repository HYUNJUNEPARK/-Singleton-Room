package com.example.room

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.room.databinding.ItemRecyclerBinding
import com.example.room.db.MemoDao
import com.example.room.db.Memo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class MemoAdapter: ListAdapter<Memo, MemoAdapter.MyHolder>(diffUtil) {
    lateinit var memoDao: MemoDao

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(binding)
    }

    inner class MyHolder(private val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        lateinit var memo: Memo

        init {
            binding.deleteButton.setOnClickListener {
                //DB 에서 item 삭제
                CoroutineScope(Dispatchers.IO).launch {
                    memoDao?.delete(memo)
                }
                //currentList 에서 item 삭제 후 갱신
                currentList.toMutableList().apply {
                    this.remove(memo)
                    submitList(this)
                }
            }

            binding.modifyButton.setOnClickListener {
                if(binding.modifyButton.text == "수정") {
                    binding.textEditor.visibility = View.VISIBLE
                    binding.modifyButton.text = "저장"
                    binding.modifyButton.setTextColor(Color.RED)
                    binding.textEditor.setText("${memo?.content}")
                } else {
                    binding.textEditor.visibility = View.INVISIBLE
                    binding.modifyButton.text = "수정"
                    binding.modifyButton.setTextColor(Color.WHITE)

                    val newMemo = Memo(
                        idx = memo.idx,
                        content = binding.textEditor.text.toString(),
                        datetime = System.currentTimeMillis()
                    )
                    //DB 에 수정 사항 갱신
                    CoroutineScope(Dispatchers.IO).launch {
                        memoDao.update(newMemo)
                    }
                    //currentList 갱신
                    val memoList = currentList.toMutableList().apply {
                        this.removeAt(memo.idx)
                        this.add(memo.idx, newMemo)
                    }
                    submitList(memoList)
                }
            }
        }

        fun bind(memo: Memo) {
            this.memo = memo
            binding.textIdx.text = "${memo.idx}"
            binding.textContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            binding.textDatetime.text = "${sdf.format(memo.datetime)}"
        }
    }

    override fun onBindViewHolder(myHolder: MyHolder, position: Int) {
        myHolder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Memo>() {
            override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
                return oldItem.idx == newItem.idx
            }
            override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
                return oldItem == newItem
            }
        }
    }
}