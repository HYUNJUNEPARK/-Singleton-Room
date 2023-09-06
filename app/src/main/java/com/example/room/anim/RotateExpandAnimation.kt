package com.example.room.anim

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import com.example.room.MainActivity.Companion.TAG

class RotateExpandAnimation {
    companion object {
        fun rotateArrow(view: View, isExpanded: Boolean): Boolean {
            return if (isExpanded) { //서브 뷰가 펼쳐져 있고 화살표는 아래로 향한 상태(기본 설정: 화살표 위로)
                view.animate().setDuration(200).rotation(0f) //화살표를 위로 돌린다.
                true
            } else { //서브 뷰가 접혀 있고 화살표는 위로 향한 상태(기본 설정: 화살표 위로)
                view.animate().setDuration(200).rotation(180f) //화살표를 아래로 돌린다.
                false
            }
        }

        fun expand(view: View) {
            val animation = expandAction(view)
            view.startAnimation(animation)
        }

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
                    view.layoutParams.height = if (interpolatedTime == 1f) { //애니메이션 동작이 끝나면 interpolatedTime == 1f
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
                    if (interpolatedTime == 1f) { //애니메이션 동작이 끝나면 interpolatedTime == 1f
                        view.visibility = View.GONE
                    } else {
                        view.layoutParams.height = (actualHeight - (actualHeight * interpolatedTime)).toInt() //뷰의 높이가 점진적으로 줄어든다.
                        view.requestLayout()
                    }
                }
            }

            animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()

            Log.d(TAG, "collapse:뷰가 사라지는 시간(ms):${animation.duration}") //ex.388ms

            view.startAnimation(animation)
        }
    }
}