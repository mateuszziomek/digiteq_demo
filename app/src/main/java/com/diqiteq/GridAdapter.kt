package com.diqiteq

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GridSquareAdapter(private val context: Context, private val itemCount: Int) :
    RecyclerView.Adapter<GridSquareAdapter.GridSquareViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridSquareViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_grid, parent, false)
        return GridSquareViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridSquareViewHolder, position: Int) {
        val color = if (position % 2 == 0) Color.BLUE else Color.RED
        holder.itemView.findViewById<View>(R.id.item_content).setBackgroundColor(color)
    }

    override fun getItemCount(): Int {
        return itemCount
    }

    inner class GridSquareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
