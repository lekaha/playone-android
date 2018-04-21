package com.playone.mobile.ui.browse

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.BufferooViewModel
import com.playone.mobile.ui.view.recycler.DisplayableItem
import com.playone.mobile.ui.view.recycler.ViewHolderBinder
import com.playone.mobile.ui.view.recycler.ViewHolderFactory

class BrowseViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var avatarImage: ImageView = view.findViewById(R.id.image_avatar)
    var nameText: TextView = view.findViewById(R.id.text_name)
    var titleText: TextView = view.findViewById(R.id.text_title)

    fun bind(bufferoo: BufferooViewModel) {
        nameText.text = bufferoo.name
        titleText.text = bufferoo.title

        Glide.with(itemView.context)
                .load(bufferoo.avatar)
                .apply(RequestOptions.circleCropTransform())
                .into(avatarImage)
    }


    class BrowseViewHolderFactory constructor(context: Context):
            ViewHolderFactory(context) {

        override fun createViewHolder(parent: ViewGroup)
                = BrowseViewHolder(LayoutInflater
                    .from(context)
                    .inflate(R.layout.item_bufferoo, parent, false))

    }

    class BrowseViewHolderBinder : ViewHolderBinder {
        override fun bind(
            viewHolder: RecyclerView.ViewHolder,
            item: DisplayableItem<*>
        ) {
            var browseViewHolder = BrowseViewHolder::class.java.cast(viewHolder)
            var bufferooViewModel = BufferooViewModel::class.java.cast(item.model())
            browseViewHolder.bind(bufferooViewModel)
        }

    }
}