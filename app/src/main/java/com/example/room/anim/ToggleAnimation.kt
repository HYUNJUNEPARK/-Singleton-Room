package com.example.room.anim

import android.graphics.drawable.Animatable
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation

class ToggleAnimation {
    companion object {
        fun toggleArrow(view: View, isExpanded: Boolean): Boolean {
            return if (isExpanded) { //서브 뷰가 펼쳐져 있고 화살표는 아래로 향한 상태
                view.animate().setDuration(200).rotation(180f)
                true
            } else { //서브 뷰가 접혀 있고 화살표는 위로 향한 상태
                view.animate().setDuration(200).rotation(0f)
                false
            }
        }

        //접힘 -> 펼침
        fun expand(view: View) {
            val animation = expandAction(view)
            view.startAnimation(animation)
        }

        //애니메이션 적용 시간
        private fun expandAction(view:View): Animation {
            view.measure(
                /*widthMeasureSpec*/ViewGroup.LayoutParams.MATCH_PARENT,
                /*heightMeasureSpec*/ViewGroup.LayoutParams.WRAP_CONTENT
            )

            val actualHeight = view.measuredHeight

            view.layoutParams.height = 0
            view.visibility = View.VISIBLE

            val animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    view.layoutParams.height = if (interpolatedTime == 1f) {
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    } else {
                        (actualHeight*interpolatedTime).toInt()
                    }
                    view.requestLayout()
                }
            }

            animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()

            view.startAnimation(animation)

            return animation
        }

        fun collapse(view: View) {
            val actualHeight = view.measuredHeight

            val animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    if (interpolatedTime == 1f) {
                        view.visibility = View.GONE
                    } else {
                        view.layoutParams.height = (actualHeight - (actualHeight * interpolatedTime)).toInt()
                        view.requestLayout()
                    }
                }
            }

            animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()
            view.startAnimation(animation)
        }
    }
}