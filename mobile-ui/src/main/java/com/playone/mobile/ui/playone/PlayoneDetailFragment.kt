package com.playone.mobile.ui.playone

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import androidx.core.os.bundleOf
import com.like.LikeButton
import com.like.OnLikeListener
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise
import com.playone.mobile.presentation.model.PlayoneView
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.BuildConfig
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.injection.module.GlideApp
import com.playone.mobile.ui.mapper.PlayoneMapper
import com.playone.mobile.ui.model.PlayoneDetailViewModel
import com.playone.mobile.ui.model.PlayoneParticipatorItemViewModel
import com.playone.mobile.ui.navigateToUri
import com.playone.mobile.ui.view.recycler.DisplayableItem
import kotlinx.android.synthetic.main.fragment_playone_detail.dateTextView
import kotlinx.android.synthetic.main.fragment_playone_detail.favoriteButton
import kotlinx.android.synthetic.main.fragment_playone_detail.iv_team_map
import kotlinx.android.synthetic.main.fragment_playone_detail.progressBar
import kotlinx.android.synthetic.main.fragment_playone_detail.rv_participation
import kotlinx.android.synthetic.main.fragment_playone_detail.textView2
import kotlinx.android.synthetic.main.item_playone_constraint.teamCoverThumb
import kotlinx.android.synthetic.main.item_playone_constraint.tv_limit
import kotlinx.android.synthetic.main.item_playone_constraint.tv_title
import java.util.Random
import javax.inject.Inject

class PlayoneDetailFragment : BaseInjectingFragment() {

    companion object {
        private const val PARAMETER_PLAYONE_ITEM = "PARAMETER_PLAYONE_ITEM"

        fun newInstance(playone: PlayoneView) = PlayoneDetailFragment().apply {
            arguments = bundleOf(PARAMETER_PLAYONE_ITEM to playone)
        }
    }

    @Inject lateinit var playoneAdapter: PlayoneAdapter
    @Inject lateinit var mapper: PlayoneMapper
    @Inject lateinit var navigator: Navigator

    private var viewModel: PlayoneDetailViewModel? = null
    private val playone by lazy {
        arguments?.getSerializable(PARAMETER_PLAYONE_ITEM) as PlayoneView
    }

    private val glideRequest by lazy { GlideApp.with(this) }

    private lateinit var staticMapUri: String

    private val observer by lazy {
        Observer<PlayoneView> {
            it?.let { playoneView ->
                setupViews(playoneView)
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_playone_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        initViewModel()

        // get ViewModel from activity
        activity?.let {
            setupRecycler()
            viewModel?.load(playone)
        }
    }

    override fun onPause() {
        super.onPause()
        favoriteButton.isLiked = false
    }

    private fun initViewModel() {

        activity?.let {
            viewModel =
                ViewModelProviders.of(it).get(PlayoneDetailViewModel::class.java).also {
                    it.observePlayoneDetail(this, observer)
                    it.observeIsProgressing(this) {
                        it.ifTrue {
                            progressBar.visibility = View.VISIBLE
                            iv_team_map.visibility = View.GONE
                            favoriteButton.visibility = View.GONE
                        } otherwise  {
                            progressBar.visibility = View.GONE
                            iv_team_map.visibility = View.VISIBLE
                            favoriteButton.visibility = View.VISIBLE
                        }

                    }

                    lifecycle::addObserver
                }
        }
    }

    private fun setupViews(playoneView: PlayoneView) {

        tv_limit.text = playoneView.limit.toString()
        tv_title.text = playoneView.name
        dateTextView.text = playoneView.date.toString()

        GlideApp.with(teamCoverThumb)
            .load(playoneView.coverUrl)
            .placeholder(R.drawable.team_pic)
            .error(R.drawable.team_pic)
            .into(teamCoverThumb)

        // TODO: for demo
        val text = makeRandomText()
        textView2.text = text

        val zoom = getString(R.string.map_zoom).toInt()
        staticMapUri = getString(R.string.static_map,
                                 zoom,
                                 getString(R.string.map_width).toInt(),
                                 getString(R.string.map_height).toInt(),
                                 getString(R.string.map_mark_color),
                                 playoneView.longitude,
                                 playoneView.latitude,
                                 BuildConfig.GOOGLE_MAP_API_KEY)

        glideRequest.load(staticMapUri).apply {
            appCompatActivity?.let {
                val circularProgressDrawable = CircularProgressDrawable(it)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()

                placeholder(circularProgressDrawable)
            }

            centerCrop()
            into(iv_team_map)
        }

        iv_team_map.setOnClickListener {
            val gmmIntentUri =
                Uri.parse(getString(R.string.map_uri,
                                    playoneView.longitude,
                                    playoneView.latitude,
                                    zoom))
            navigator.navigateToUri<PlayoneActivity>(
                this@PlayoneDetailFragment,
                Intent.ACTION_VIEW,
                gmmIntentUri,
                "com.google.android.apps.maps")
        }

        favoriteButton.isLiked = playoneView.isFavorited
        favoriteButton.setOnLikeListener {
            liked = {
                viewModel?.setFavorite(it.isLiked)
            }

            unLiked = {
                viewModel?.setFavorite(it.isLiked)
            }
        }

        // TODO: here is for demo should fix
        val list = makeRandomDisplayableItemList()

        playoneAdapter.update(list)
        playoneAdapter.notifyDataSetChanged()
    }

    private fun changeFavoriteIcon() {

    }

    @VisibleForTesting
    private fun makeRandomText() = "我々は福岡バスケの同好会です。国籍、男女は問いません。バスケが好きなら初心者でも大歓迎です。"

    @VisibleForTesting
    private fun makeRandomDisplayableItemList():
        List<DisplayableItem<PlayoneParticipatorItemViewModel>> {

        val r = Random()
        r.setSeed(System.currentTimeMillis())
        val randomJoinedNumber = r.nextInt() % 10
        val list = mutableListOf<DisplayableItem<PlayoneParticipatorItemViewModel>>()
        for (i in 0..randomJoinedNumber) {
            val item = DisplayableItem.toDisplayableItem(
                PlayoneParticipatorItemViewModel(),
                PlayoneParticipatorItemViewModel.DISPLAY_TYPE_PARTICIPATOR
            )
            list.add(item)
        }

        return list.toList()
    }

    private fun setupRecycler() {

        rv_participation.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = playoneAdapter
        }
    }
}

class OnLikeListenerBuilder {
    var liked: (LikeButton) -> Unit = {}
    var unLiked: (LikeButton) -> Unit = {}

    fun build() = object: OnLikeListener {
        override fun liked(var1: LikeButton) = this@OnLikeListenerBuilder.liked(var1)
        override fun unLiked(var1: LikeButton) = this@OnLikeListenerBuilder.unLiked(var1)
    }
}

fun LikeButton.setOnLikeListener(builder: OnLikeListenerBuilder.() -> Unit) =
    this.setOnLikeListener(OnLikeListenerBuilder().apply(builder).build())