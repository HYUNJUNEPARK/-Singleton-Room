package com.example.room.callback

import com.example.room.db.Memo
import com.example.room.db.MemoUiDto

class ClickListener {
    interface ClickEventListener {
        fun onModifyLongClicked(item: MemoUiDto)
        fun onModifyShortClicked(newItem: Memo)
        fun onDeleteClicked(item: Memo)
    }
}