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
import com.module.databasemanager.Memo
import com.module.databasemanager.MemoDao
import com.module.databasemanager.MemoViewModel
import java.text.SimpleDateFormat
import java.util.*

class MemoAdapter(val context: Context): ListAdapter<Memo, MemoAdapter.ViewHolder>(diffUtil) {
    private val viewModel: MemoViewModel by (context as ComponentActivity).viewModels()
    lateinit var memoDao: MemoDao

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var memo: Memo
        private var idx: Int? = null

        init {
            binding.deleteButton.setOnClickListener {
                viewModel.deleteData(context, memo)
            }

            binding.modifyButton.setOnClickListener {
                if(binding.modifyButton.text == "수정") {
                    binding.textEditor.visibility = View.VISIBLE
                    binding.modifyButton.text = "저장"
                    binding.modifyButton.setTextColor(Color.RED)
                    binding.textEditor.setText(memo.content)
                } else {
                    binding.textEditor.visibility = View.INVISIBLE
                    binding.modifyButton.text = "수정"
                    binding.modifyButton.setTextColor(Color.WHITE)

                    viewModel.updateData(
                        context = context,
                        newMemo = Memo(
                            id = memo.id,
                            content = binding.textEditor.text.toString(),
                            datetime = System.currentTimeMillis()
                        ),
                        idx = idx!!
                    )
                }
            }
        }

        fun bind(memo: Memo, idx: Int) {
            this.memo = memo
            this.idx = idx
            binding.textIdx.text = "${memo.id}"
            binding.textContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm", Locale.KOREA)
            binding.textDatetime.text = sdf.format(memo.datetime)
        }
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(currentList[position], position)
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