package com.hirno.compose.collection.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hirno.compose.collection.databinding.CollectionItemBinding
import com.hirno.compose.collection.model.collection.CollectionItemModel

/**
 * The [RecyclerView] adapter of [MainFragment]
 *
 * @property viewModel The fragment view model
 */
class MainAdapter(
    private val viewModel: MainViewModel,
) : ListAdapter<CollectionItemModel, MainAdapter.ArtViewHolder>(
    CollectionsDiffCallback(),
) {

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        return ArtViewHolder.from(parent)
    }

    class ArtViewHolder private constructor(
        private val binding: CollectionItemBinding,
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        companion object {
            fun from(parent: ViewGroup): ArtViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CollectionItemBinding.inflate(layoutInflater, parent, false)

                return ArtViewHolder(binding)
            }
        }

        fun bind(viewModel: MainViewModel, item: CollectionItemModel) {
            binding.viewmodel = viewModel
            binding.collection = item
            binding.executePendingBindings()
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class CollectionsDiffCallback : DiffUtil.ItemCallback<CollectionItemModel>() {
    override fun areItemsTheSame(oldItem: CollectionItemModel, newItem: CollectionItemModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CollectionItemModel, newItem: CollectionItemModel): Boolean {
        return oldItem == newItem
    }
}