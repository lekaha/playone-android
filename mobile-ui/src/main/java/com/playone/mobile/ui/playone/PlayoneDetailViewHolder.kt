package com.playone.mobile.ui.playone

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.PlayoneParticipatorItemViewModel
import com.playone.mobile.ui.view.recycler.DisplayableItem
import com.playone.mobile.ui.view.recycler.ViewHolderBinder
import com.playone.mobile.ui.view.recycler.ViewHolderFactory

class PlayoneDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(playoneParticipatorItemViewModel: PlayoneParticipatorItemViewModel) {

//        Glide.with(itemView.context)
//                .load(bufferoo.avatar)
//                .apply(RequestOptions.circleCropTransform())
//                .into(avatarImage)
    }

    class PlayoneViewHolderFactory constructor(context: Context) : ViewHolderFactory(context) {

        override fun createViewHolder(parent: ViewGroup) =
            PlayoneDetailViewHolder(LayoutInflater
                                  .from(context)
                                  .inflate(R.layout.item_participation, parent, false))
    }

    class PlayoneViewHolderBinder : ViewHolderBinder {

        override fun bind(
            viewHolder: RecyclerView.ViewHolder,
            item: DisplayableItem<*>
        ) {

            val playoneViewHolder = PlayoneDetailViewHolder::class.java.cast(viewHolder)
            val playoneViewModel = PlayoneParticipatorItemViewModel::class.java.cast(item.model())
            playoneViewHolder.bind(playoneViewModel)
        }
    }
}