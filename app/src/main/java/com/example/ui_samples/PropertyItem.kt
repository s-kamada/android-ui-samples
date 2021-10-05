package com.example.ui_samples

import com.example.ui_samples.databinding.CellPropertyBinding
import com.xwray.groupie.databinding.BindableItem

class PropertyItem(
    private val viewModel: MainViewModel,
    private val text: String
) : BindableItem<CellPropertyBinding>() {
    override fun getLayout(): Int = R.layout.cell_property

    override fun bind(viewBinding: CellPropertyBinding, position: Int) {
        viewBinding.text = text
        viewBinding.vm = viewModel
    }
}
