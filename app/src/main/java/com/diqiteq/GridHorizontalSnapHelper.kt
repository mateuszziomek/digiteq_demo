package com.diqiteq

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

class GridHorizontalSnapHelper(private val rows: Int, private val columns: Int) : SnapHelper() {

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        val out = IntArray(2)
        out[0] = distanceToStart(targetView, layoutManager)
        out[1] = 0 // we are only scrolling horizontally
        return out
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {
        if (layoutManager !is GridHorizontalLayoutManager) {
            return RecyclerView.NO_POSITION
        }

        val itemCount = layoutManager.itemCount
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION
        }

        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        val snapDistance = rows * columns

        return if (velocityX > 0) {
            val currentPage = firstVisibleItemPosition / snapDistance
            val nextPageFirstItem = (currentPage + 1) * snapDistance
            if (nextPageFirstItem < itemCount) nextPageFirstItem else RecyclerView.NO_POSITION
        } else {
            val currentPage =
                if (firstVisibleItemPosition % snapDistance == 0) firstVisibleItemPosition / snapDistance - 1 else firstVisibleItemPosition / snapDistance
            val prevPageLastItem = currentPage * snapDistance - 1
            if (prevPageLastItem >= 0) prevPageLastItem else RecyclerView.NO_POSITION
        }
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        if (layoutManager !is GridHorizontalLayoutManager) {
            return null
        }

        val snapDistance = rows * columns
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        return layoutManager.findViewByPosition(
            if (firstVisibleItemPosition % snapDistance == 0)
                firstVisibleItemPosition
            else
                (firstVisibleItemPosition / snapDistance) * snapDistance
        )
    }

    private fun distanceToStart(targetView: View, layoutManager: RecyclerView.LayoutManager): Int {
        val params = targetView.layoutParams as RecyclerView.LayoutParams
        val leftMargin = params.leftMargin
        return layoutManager.getDecoratedLeft(targetView) - leftMargin
    }

    override fun calculateScrollDistance(velocityX: Int, velocityY: Int): IntArray {
        return intArrayOf(velocityX, 0) // Only support horizontal scrolling
    }
}
