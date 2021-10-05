package com.example.ui_samples

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.ui_samples.databinding.ActivityMainBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class MainActivity : AppCompatActivity() {
    /** ref: viewmodel生成の簡単化・共通化パターン(Factory) https://suihan74.github.io/posts/2020/09_14_01_provide_view_model/ */
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = GroupAdapter<GroupieViewHolder>()
        binding.propertiesRecyclerView.adapter = adapter

        listOf("cell1", "cell2", "cell3", "cell4", "cell5", "cell6", "cell7").forEach {
            adapter.add(PropertyItem(viewModel, it))
        }
    }
}
