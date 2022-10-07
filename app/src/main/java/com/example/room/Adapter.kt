package com.example.room

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.room.databinding.ItemRecyclerBinding
import com.example.room.db.MemoDao
import com.example.room.db.Memo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class Adapter: RecyclerView.Adapter<Adapter.MyHolder>() {
    var memoList = mutableListOf<Memo>()
    var memoDao: MemoDao ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyHolder(binding)
    }

    inner class MyHolder(private val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        lateinit var memo: Memo

        init {
            binding.deleteButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    memoDao?.delete(memo)
                }
                memoList.remove(memo)
                notifyDataSetChanged()
            }
            binding.modifyButton.setOnClickListener {
                if(binding.modifyButton.text == "수정"){
                    binding.textEditor.visibility = View.VISIBLE
                    binding.modifyButton.text = "저장"
                    binding.modifyButton.setTextColor(Color.RED)
                    binding.textEditor.setText("${memo?.content}")
                } else {
                    binding.textEditor.visibility = View.INVISIBLE
                    binding.modifyButton.text = "수정"
                    binding.modifyButton.setTextColor(Color.WHITE)

                    val content = binding.textEditor.text.toString()
                    val datetime = System.currentTimeMillis()
                    val memo = Memo(memo.idx, content, datetime)

                    CoroutineScope(Dispatchers.IO).launch {
                        memoDao?.update(memo)
                    }

                    memoList.clear()

                    CoroutineScope(Dispatchers.IO).launch {
                        memoList.addAll(memoDao?.getAll()?: listOf())
                    }

                    notifyDataSetChanged()
                }
            }
        }

        fun setContents(memo: Memo) {
            this.memo = memo
            binding.textIdx.text = "${memo.idx}"
            binding.textContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            binding.textDatetime.text = "${sdf.format(memo.datetime)}"
        }
    }

    override fun onBindViewHolder(myHolder: MyHolder, position: Int) {
        val memo = memoList[position]
        myHolder.setContents(memo)
    }

    override fun getItemCount(): Int {
        return memoList.size
    }
}