package com.diqiteq

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridHorizontalLayoutManager(
    context: Context,
    reverseLayout: Boolean,
    private val rows: Int,
    private val columns: Int,
) : LinearLayoutManager(context, HORIZONTAL, reverseLayout) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)

        if (state?.isPreLayout == true) return

        recycler?.let {
            if (itemCount == 0) {
                removeAndRecycleAllViews(it)
                return
            }

            detachAndScrapAttachedViews(it)

            val width = width
            val height = height

            val rowWidth = width / columns
            val columnHeight = height / rows

            var rowIndex = 0
            var columnIndex = 0

            for (i in 0 until itemCount) {
                val view = it.getViewForPosition(i)
                addView(view)

                measureChildWithMargins(view, 0, 0)

                val left = columnHeight * columnIndex
                val top = rowWidth * rowIndex
                val right = left + rowWidth
                val bottom = top + columnHeight

                layoutDecorated(view, left, top, right, bottom)

                columnIndex++
                if (columnIndex == columns) {
                    columnIndex = 0
                    rowIndex++
                }
            }
        }
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun canScrollVertically(): Boolean {
        return false
    }
}
