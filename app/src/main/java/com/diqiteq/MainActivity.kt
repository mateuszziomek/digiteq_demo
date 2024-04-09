package com.diqiteq

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diqiteq.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecycler(
            MainConfig(
                rows = 2,
                columns = 5,
                reverseLayout = false
            )
        )
    }

    private fun setupRecycler(config: MainConfig) {
        val gridLayoutManager = CustomGridLayoutManager(
            rows = config.rows,
            columns = config.columns
        )
        val gridSnapHelper = GridHorizontalSnapHelper(
            rows = config.rows,
            columns = config.columns
        )
        val gridAdapter = GridSquareAdapter(this, ITEMS_COUNT)

        with(binding.mainRecycler) {
            layoutManager = gridLayoutManager
            adapter = gridAdapter
            gridSnapHelper.attachToRecyclerView(this)
        }
    }

    data class MainConfig(
        val rows: Int,
        val columns: Int,
        val reverseLayout: Boolean
    )

    companion object {
        private const val ITEMS_COUNT = 20 // for demo purposes, possibly data driven
    }
}