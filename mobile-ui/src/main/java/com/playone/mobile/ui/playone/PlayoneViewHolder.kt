package com.playone.mobile.ui.playone

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.PlayoneListItemViewModel
import com.playone.mobile.ui.view.recycler.DisplayableItem
import com.playone.mobile.ui.view.recycler.ViewHolderBinder
import com.playone.mobile.ui.view.recycler.ViewHolderFactory

class PlayoneViewHolder(view: View): RecyclerView.ViewHolder(view) {
//    var avatarImage: ImageView = view.findViewById(R.id.image_avatar)
    var nameText: TextView = view.findViewById(R.id.playone_title)
    var limitText: TextView = view.findViewById(R.id.playone_limit)

    fun bind(playoneListItem: PlayoneListItemViewModel) {
        nameText.text = playoneListItem.name
        limitText.text = playoneListItem.totalNumber.toString()

//        Glide.with(itemView.context)
//                .load(bufferoo.avatar)
//                .apply(RequestOptions.circleCropTransform())
//                .into(avatarImage)
    }


    class PlayoneViewHolderFactory constructor(context: Context):
            ViewHolderFactory(context) {

        override fun createViewHolder(parent: ViewGroup)
                = PlayoneViewHolder(LayoutInflater
                    .from(context)
                    .inflate(R.layout.item_playone, parent, false))

    }

    class PlayoneViewHolderBinder : ViewHolderBinder {
        override fun bind(viewHolder: RecyclerView.ViewHolder,
                          item: DisplayableItem<*>) {
            val playoneViewHolder = PlayoneViewHolder::class.java.cast(viewHolder)
            val playoneViewModel = PlayoneListItemViewModel::class.java.cast(item.model())
            playoneViewHolder.bind(playoneViewModel)
        }

    }
}