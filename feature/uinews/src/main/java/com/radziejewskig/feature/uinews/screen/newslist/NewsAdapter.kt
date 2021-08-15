package com.radziejewskig.feature.uinews.screen.newslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.radziejewskig.feature.uinews.databinding.ItemNewsBinding
import com.radziejewskig.presentation.model.NewsPresentationModel
import com.radziejewskig.sharedui.extension.loadImage
import com.radziejewskig.sharedui.extension.toDateText

class NewsAdapter(
    private val onItemClicked: (newsId: String) -> Unit
): PagingDataAdapter<NewsPresentationModel, NewsAdapter.TitleViewHolder>(newsComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsAdapter.TitleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TitleViewHolder(ItemNewsBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: NewsAdapter.TitleViewHolder, position: Int) {
        val item: NewsPresentationModel? = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class TitleViewHolder(
        private val binding: ItemNewsBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                if(bindingAdapterPosition >= 0){
                    getItem(bindingAdapterPosition)?.let {
                        onItemClicked(it.id)
                    }
                }
            }
        }

        fun bind(item: NewsPresentationModel?) = binding.run {
            item?.let {
                val context = binding.root.context
                itemNewsImage.loadImage(
                    context,
                    item.urlImage
                )
                itemNewsTitle.text = item.headline
                itemNewsDate.text = item.publishedTime.toDateText(context)
                itemNewsTitle.alpha = if(item.isRead) 0.5f else 1f
            }

        }

    }

    companion object {
        private val newsComparator = object: DiffUtil.ItemCallback<NewsPresentationModel>() {
            override fun areItemsTheSame(
                oldItem: NewsPresentationModel,
                newItem: NewsPresentationModel
            ) = oldItem.id == newItem.id
            override fun areContentsTheSame(
                oldItem: NewsPresentationModel,
                newItem: NewsPresentationModel
            ) = oldItem == newItem
        }
    }

 }
