package com.diqiteq

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CustomGridLayoutManager(
    private val rows: Int,
    private val columns: Int,
    private val reverseLayout: Boolean = false
) : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)

        val totalItems = itemCount

        var x = if (reverseLayout) columns - 1 else 0
        var y = 0

        for (i in 0 until totalItems) {
            val view = recycler.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)

            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)

            layoutDecorated(view, x * width, y * height, (x + 1) * width, (y + 1) * height)

            if (reverseLayout) {
                if (x > 0) {
                    x--
                } else {
                    x = columns - 1
                    if (y < rows - 1) {
                        y++
                    } else {
                        y = 0
                    }
                }
            } else {
                if (x < columns - 1) {
                    x++
                } else {
                    x = 0
                    if (y < rows - 1) {
                        y++
                    } else {
                        y = 0
                    }
                }
            }
        }
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        offsetChildrenHorizontal(-dx)
        return dx
    }
}
