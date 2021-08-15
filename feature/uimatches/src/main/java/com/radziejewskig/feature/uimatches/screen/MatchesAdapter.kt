package com.radziejewskig.feature.uimatches.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.radziejewskig.feature.uimatches.R
import com.radziejewskig.feature.uimatches.databinding.ItemMatchBinding
import com.radziejewskig.presentation.model.MatchSchemaPresentationModel
import com.radziejewskig.sharedui.extension.getColorFromAttr

class MatchesAdapter: ListAdapter<MatchSchemaPresentationModel, MatchesAdapter.MatchViewHolder>(matchComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchesAdapter.MatchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MatchViewHolder(ItemMatchBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val item: MatchSchemaPresentationModel? = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class MatchViewHolder(
        private val binding: ItemMatchBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MatchSchemaPresentationModel?) = binding.run {
            item?.let {
                val context = binding.root.context
                itemMatchScore.setTextColor(context.getColorFromAttr(
                    if(bindingAdapterPosition % 2 == 0) {
                        R.attr.colorPrimary
                    } else {
                        R.attr.colorPrimaryVariant
                    }
                ))
                itemMatchScore.text = context.getString(
                    R.string.match_score_format,
                    item.scoreA,
                    item.scoreB
                )
                itemMatchTeamA.text = item.teamAName
                itemMatchTeamB.text = item.teamBName
            }
        }
    }

    companion object {
        private val matchComparator = object: DiffUtil.ItemCallback<MatchSchemaPresentationModel>() {
            override fun areItemsTheSame(
                oldItem: MatchSchemaPresentationModel,
                newItem: MatchSchemaPresentationModel
            ) = oldItem.id == newItem.id
            override fun areContentsTheSame(
                oldItem: MatchSchemaPresentationModel,
                newItem: MatchSchemaPresentationModel
            ) = oldItem == newItem
        }
    }

}
