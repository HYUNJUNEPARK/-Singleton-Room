package com.example.room.callback

import com.example.room.db.Memo

class ClickListener {
    interface ClickEventListener {
        fun onModifyLongClicked(item: Memo)
        fun onModifyShortClicked(newItem: Memo)
        fun onDeleteClicked(item: Memo)
    }
}