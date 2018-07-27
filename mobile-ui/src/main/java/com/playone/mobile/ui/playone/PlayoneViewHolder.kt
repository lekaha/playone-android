package com.playone.mobile.ui.playone

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.playone.mobile.ui.R
import com.playone.mobile.ui.injection.module.GlideApp
import com.playone.mobile.ui.model.PlayoneListItemViewModel
import com.playone.mobile.ui.view.recycler.DisplayableItem
import com.playone.mobile.ui.view.recycler.OnItemClickedListener
import com.playone.mobile.ui.view.recycler.ViewHolderBinder
import com.playone.mobile.ui.view.recycler.ViewHolderFactory
import kotlinx.android.synthetic.main.item_playone_constraint.view.cl_playone
import kotlinx.android.synthetic.main.item_playone_constraint.view.teamCoverThumb
import kotlinx.android.synthetic.main.item_playone_constraint.view.tv_distance
import kotlinx.android.synthetic.main.item_playone_constraint.view.tv_limit
import kotlinx.android.synthetic.main.item_playone_constraint.view.tv_title

class PlayoneViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    //    var avatarImage: ImageView = view.findViewById(R.id.image_avatar)
    private val clPlayone by lazy { view.cl_playone }
    private val tvName by lazy { view.tv_title }
    private val tvLimit by lazy { view.tv_limit }
    private val tvDistance by lazy { view.tv_distance }
    private val teamCoverThumb by lazy { view.teamCoverThumb }

    fun bind(
        playoneListItem: PlayoneListItemViewModel,
        listener: OnItemClickedListener<PlayoneListItemViewModel>
    ) {

        clPlayone.setOnClickListener {
            listener(view, playoneListItem)
        }
        tvName.text = playoneListItem.name
        tvLimit.text = playoneListItem.totalNumber.toString()

        GlideApp.with(teamCoverThumb)
            .load(playoneListItem.coverUrl)
            .placeholder(R.drawable.team_pic)
            .error(R.drawable.team_pic)
            .into(teamCoverThumb)
    }

    class PlayoneViewHolderFactory constructor(context: Context) : ViewHolderFactory(context) {

        override fun createViewHolder(parent: ViewGroup) =
            PlayoneViewHolder(LayoutInflater
                                  .from(context)
                                  .inflate(R.layout.item_playone_constraint, parent, false))

    }

    class PlayoneViewHolderBinder : ViewHolderBinder {

        override fun bind(
            viewHolder: RecyclerView.ViewHolder,
            item: DisplayableItem<*>
        ) {
            val playoneViewHolder = PlayoneViewHolder::class.java.cast(viewHolder)
            val playoneViewModel = PlayoneListItemViewModel::class.java.cast(item.model())
            val itemClickedListener =
                item.click() as OnItemClickedListener<PlayoneListItemViewModel>

            playoneViewHolder.bind(playoneViewModel, itemClickedListener)
        }

    }
}