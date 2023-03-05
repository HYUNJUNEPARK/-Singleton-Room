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
import com.module.databasemanager.data.Memo
import com.module.databasemanager.data.MemoDao
import com.module.databasemanager.ui.MemoViewModel
import java.text.SimpleDateFormat
import java.util.*

class MemoAdapter(private val context: Context, val listener: ClickEventListener?): ListAdapter<Memo, MemoAdapter.ViewHolder>(diffUtil) {
    private val viewModel: MemoViewModel by (context as ComponentActivity).viewModels()
    lateinit var memoDao: MemoDao

    interface ClickEventListener {
        fun onLongClickEvent(item: Memo)
        fun onShortClickEvent(item: Memo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(currentList[position], position)
    }

    inner class ViewHolder(private val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var memo: Memo
        private var idx: Int? = null

//        init {
//            binding.modifyButton.setOnClickListener {
//                if(binding.modifyButton.text == "수정") {
//                    binding.textEditor.visibility = View.VISIBLE
//                    binding.modifyButton.text = "저장"
//                    binding.modifyButton.setTextColor(Color.RED)
//                    binding.textEditor.setText(memo.content)
//                } else {
//                    binding.textEditor.visibility = View.INVISIBLE
//                    binding.modifyButton.text = "수정"
//                    binding.modifyButton.setTextColor(Color.WHITE)
//
//                    viewModel.updateData(
//                        context = context,
//                        newMemo = Memo(
//                            id = memo.id,
//                            content = binding.textEditor.text.toString(),
//                            datetime = System.currentTimeMillis()
//                        ),
//                        idx = idx!!
//                    )
//                }
//            } //binding
//        } //init

        fun bind(item: Memo, idx: Int) {
            this.memo = item
            this.idx = idx
            binding.textIdx.text = "${item.id}"
            binding.textContent.text = item.content
            val dateFormat = SimpleDateFormat("yyyy/MM/dd hh:mm", Locale.KOREA)
            binding.textDatetime.text = dateFormat.format(item.datetime)

            binding.deleteButton.setOnClickListener {
                viewModel.deleteData(context, item)
            }


            binding.modifyButton.setOnLongClickListener {
                listener?.onLongClickEvent(item)
                return@setOnLongClickListener true
            }

            binding.modifyButton.setOnClickListener {
                listener?.onShortClickEvent(item)
            }
        }
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