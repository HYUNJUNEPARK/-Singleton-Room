package com.example.room.db

//리사이클러 뷰 UI 에서 사용될 DTO
data class MemoUiDto(
    val id: Long,
    val content: String,
    val dateTime: Long,
    var isExpanded: Boolean = false, //접힌 상태
    var isClamped: Boolean = false, //스와이프 상태
)
