package com.mufiid.utils.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mufiid.core.data.Equatable
import com.mufiid.utils.R

class GenericAdapter<T: Equatable>(
    @LayoutRes private val layoutRes: Int,
    @LayoutRes private val layoutPlaceholder: Int = R.layout.util_item_loading,
    @LayoutRes private val layoutEmpty: Int = R.layout.util_item_empty,
    @LayoutRes private val layoutError: Int = R.layout.util_item_error
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Equatable>() {
        override fun areItemsTheSame(oldItem: Equatable, newItem: Equatable): Boolean {
            return oldItem.isEqual(newItem)
        }

        override fun areContentsTheSame(oldItem: Equatable, newItem: Equatable): Boolean {
            return oldItem.isEqual(newItem)
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    private var items: List<Equatable>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}