package com.example.room

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.room.databinding.ItemRecyclerBinding
import com.module.databasemanager.data.Memo
import java.text.SimpleDateFormat
import java.util.*

class MemoAdapter(private val context: Context, val listener: ClickEventListener?): ListAdapter<Memo, MemoAdapter.ViewHolder>(diffUtil) {

    interface ClickEventListener {
        fun onModifyButtonLongClickEvent(item: Memo)
        fun onModifyButtonShortClickEvent(newItem: Memo)
        fun onDeleteButtonShortClickEvent(item: Memo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(currentList[position])
    }

    inner class ViewHolder(private val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Memo) {
            binding.textIdx.text = "${item.id}"
            binding.textContent.text = item.content
            val dateFormat = SimpleDateFormat("yyyy/MM/dd hh:mm", Locale.KOREA)
            binding.textDatetime.text = dateFormat.format(item.dateTime)

            binding.deleteButton.setOnClickListener {
                listener?.onDeleteButtonShortClickEvent(item)
            }

            binding.modifyButton.setOnClickListener {
                if(binding.modifyButton.text == "수정") {
                    binding.textEditor.visibility = View.VISIBLE
                    binding.modifyButton.text = "저장"
                    binding.modifyButton.setTextColor(Color.RED)
                    binding.textEditor.setText(item.content)
                } else {
                    binding.textEditor.visibility = View.INVISIBLE
                    binding.modifyButton.text = "수정"
                    binding.modifyButton.setTextColor(Color.WHITE)

                    val newItem = Memo(
                        id = item.id,
                        content = binding.textEditor.text.toString(),
                        dateTime = System.currentTimeMillis()
                    )

                    listener?.onModifyButtonShortClickEvent(newItem)
                }
            }

            binding.modifyButton.setOnLongClickListener {
                listener?.onModifyButtonLongClickEvent(item)
                return@setOnLongClickListener true
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