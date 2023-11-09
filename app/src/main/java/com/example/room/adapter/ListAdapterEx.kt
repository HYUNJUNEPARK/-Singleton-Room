package com.example.room.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.room.MainActivity.Companion.TAG
import com.example.room.anim.RotateExpandAnimation
import com.example.room.databinding.ItemRecyclerBinding
import com.example.room.db.Memo
import com.example.room.callback.ClickListener
import com.example.room.db.MemoUiDto
import java.text.SimpleDateFormat
import java.util.*

class ListAdapterEx(
    val itemClickListener: ClickListener.ClickEventListener?
): ListAdapter<MemoUiDto, ListAdapterEx.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(currentList[position])
    }

    inner class ViewHolder(
        private val binding: ItemRecyclerBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun setClamped(isClamped: Boolean){
            getItem(adapterPosition).isClamped = isClamped
        }

        fun getClamped(): Boolean{
            return getItem(adapterPosition).isClamped
        }

        fun bind(item: MemoUiDto) {
            binding.textIdx.text = "${item.id}"

            binding.textContent.text = item.content

            val dateFormat = SimpleDateFormat("yyyy/MM/dd hh:mm", Locale.KOREA)
            binding.textDatetime.text = dateFormat.format(item.dateTime)

            //삭제 버튼
            binding.deleteButton.setOnClickListener {
                itemClickListener?.onDeleteClicked(item = Memo(
                    id = item.id,
                    content = item.content,
                    dateTime = item.dateTime
                ))
            }

            //수정, 저장 버튼
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

                    itemClickListener?.onModifyShortClicked(newItem)
                }
            }


            binding.modifyButton.setOnLongClickListener {
                itemClickListener?.onModifyLongClicked(item)
                return@setOnLongClickListener true
            }

            binding.foldButton.setOnClickListener { view ->
                Log.d(TAG, "==============================")
                Log.d(TAG, "클릭 이벤트 전 데이터:\n$currentList \n클릭된 아이템:$item")

                val isExpanded = rotateExpandView(
                    isExpanded = item.isExpanded,
                    eventTriggerView = view,
                    targetExpandView = binding.foldableTextView
                )

                item.isExpanded = isExpanded

                Log.d(TAG, "클릭 이벤트 후 데이터:\n$currentList \n클릭된 아이템:$item")
                Log.d(TAG, "==============================")
            }
        }

        /**
         * @param isExpanded 이벤트 발생 시점. 뷰의 접힌 상태
         * @param eventTriggerView 이벤트를 발생시키는 뷰
         * @param targetExpandView 이벤트로 인해 변화되는 뷰
         *
         * @return 파라미터로 받은 isExpanded 속성을 반대로 바꿔 반환한다.
         */
        private fun rotateExpandView(
            isExpanded: Boolean,
            eventTriggerView: View,
            targetExpandView: TextView,
        ): Boolean {
            if (isExpanded) { //펼쳐진 상태
                RotateExpandAnimation.collapse(targetExpandView) //뷰를 접는다.
            } else { //접힌 상태
                RotateExpandAnimation.expand(targetExpandView) //뷰를 펼친다.
            }

            return RotateExpandAnimation.rotateArrow(eventTriggerView, isExpanded) //화살표를 회전 시키고, 파라미터로 전달받은 isExpanded 의 반대값을 리턴
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MemoUiDto>() {
            override fun areItemsTheSame(oldItem: MemoUiDto, newItem: MemoUiDto): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: MemoUiDto, newItem: MemoUiDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}