package com.example.room

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.room.databinding.ItemRecyclerBinding
import com.example.room.db.Memo
import com.example.room.db.MemoDao
import com.example.room.vm.MemoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class MemoAdapter(val context: Context): ListAdapter<Memo, MemoAdapter.ViewHolder>(diffUtil) {
    lateinit var memoDao: MemoDao
    private val viewModel: MemoViewModel by (context as ComponentActivity).viewModels()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.recyclerViewItem = this
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        lateinit var memo: Memo

        init {
            binding.deleteButton.setOnClickListener {
                viewModel.deleteData(context, memo)
            }

            binding.modifyButton.setOnClickListener {
                if(binding.modifyButton.text == "수정") {
                    binding.textEditor.visibility = View.VISIBLE
                    binding.modifyButton.text = "저장"
                    binding.modifyButton.setTextColor(Color.RED)
                    binding.textEditor.setText("${memo.content}")
                } else {
                    binding.textEditor.visibility = View.INVISIBLE
                    binding.modifyButton.text = "수정"
                    binding.modifyButton.setTextColor(Color.WHITE)

                    val newMemo = Memo(
                        id = memo.id,
                        content = binding.textEditor.text.toString(),
                        datetime = System.currentTimeMillis()
                    )

                    //TODO MemoViewModel 53-
                    //DB 에 수정 사항 갱신
//                    CoroutineScope(Dispatchers.IO).launch {
//                        memoDao.update(newMemo)
//                    }
                    //currentList 갱신
/*                    val memoList = currentList.toMutableList().apply {
                        this.removeAt(memo.idx)
                        this.add(memo.idx, newMemo)
                    }*/
                    //submitList(memoList)
                }
            }
        }

        fun bind(memo: Memo) {
            this.memo = memo
            binding.textIdx.text = "${memo.id}"
            binding.textContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            binding.textDatetime.text = "${sdf.format(memo.datetime)}"
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Memo>() {
            override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
                return oldItem == newItem
            }
        }
    }
}