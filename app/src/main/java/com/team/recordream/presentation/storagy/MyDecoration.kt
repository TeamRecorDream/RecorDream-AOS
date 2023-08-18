package com.team.recordream.presentation.storagy

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MyDecoration(private val divHeight: Int, private val rowcount: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        // 개별 항목을 꾸밀 때 호출됨
        outRect: Rect, // 출력을 받기 위한 rect
        view: View, // 꾸미는 차일드 뷰
        parent: RecyclerView, // 이 itemdecoration이 꾸미고 있는 recyclerView
        state: RecyclerView.State, // recyclerview의 현재 상태
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view) // 어댑터에서 지정된 view에 해당하는 위치를 반환하는 메서드다.

        // followerRecyclerview
        if (rowcount == 1) { // 행이 1개일때
            outRect.set(
                divHeight / 2,
                divHeight,
                divHeight / 2,
                divHeight,
            ) // left, top, right, bottom
        } else { // repositoryrecyclerview
            outRect.top = divHeight
            outRect.bottom = divHeight / divHeight
            if (position % rowcount == 0) {
                outRect.left = divHeight
                outRect.right = divHeight / 2
            } else {
                outRect.left = divHeight / 2
                outRect.right = divHeight
            }
        }
        if (position >= 2) {
            val column = position % rowcount // item column
            outRect.apply {
                // divHeight - column * ((1f / rowcount) * divHeight)
                left = divHeight - column * divHeight / rowcount
                // (column + 1) * ((1f / rowcount) * divHeight)
                right = (column + 1) * divHeight / rowcount
//                if (position < rowcount) top = divHeight
//                bottom = divHeight
            }
        }
    }
}
