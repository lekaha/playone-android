package com.playone.mobile.ui.playone

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.playone.mobile.ext.DEFAULT_STR
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.R
import com.playone.mobile.ui.mapper.PlayoneMapper
import com.playone.mobile.ui.model.PlayoneDetailViewModel
import com.playone.mobile.ui.model.PlayoneParticipatorItemViewModel
import com.playone.mobile.ui.view.recycler.DisplayableItem
import kotlinx.android.synthetic.main.fragment_playone_detail.iv_team_map
import kotlinx.android.synthetic.main.fragment_playone_detail.rv_participation
import javax.inject.Inject

class PlayoneDetailFragment : BaseInjectingFragment() {

    companion object {
        const val PARAMETER_PLAYONE_ID = "parameter playone id"

        fun newInstance(playoneId: String = DEFAULT_STR) = PlayoneDetailFragment().apply {
            arguments = bundleOf(PARAMETER_PLAYONE_ID to playoneId)
        }
    }

    @Inject lateinit var playoneAdapter: PlayoneAdapter
    @Inject lateinit var mapper: PlayoneMapper

    private var viewModel: PlayoneDetailViewModel? = null
    private val playoneId by lazy { arguments?.getString(PARAMETER_PLAYONE_ID) ?: DEFAULT_STR }
    private lateinit var staticMapUri: String

    override fun getLayoutId() = R.layout.fragment_playone_detail

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        // get ViewModel from activity
        activity?.let {
            setupRecycler()
            viewModel?.load(playoneId)
        }

        playoneAdapter.update(listOf(DisplayableItem.toDisplayableItem(
            PlayoneParticipatorItemViewModel(),
            PlayoneParticipatorItemViewModel.DISPLAY_TYPE_PARTICIPATOR),
                                     DisplayableItem.toDisplayableItem(
                                         PlayoneParticipatorItemViewModel(),
                                         PlayoneParticipatorItemViewModel.DISPLAY_TYPE_PARTICIPATOR)))
        playoneAdapter.notifyDataSetChanged()
    }

    private fun initViewModel() {

        activity?.let {
            viewModel =
                ViewModelProviders.of(it).get(PlayoneDetailViewModel::class.java).also {
                    it.fetchDetailData().observe(this, Observer {
                        it?.let {
                            staticMapUri = getString(R.string.static_map,
                                                     it.latitude,
                                                     it.longitude,
                                                     getString(R.string.map_zoom).toInt(),
                                                     getString(R.string.map_width).toInt(),
                                                     getString(R.string.map_height).toInt(),
                                                     getString(R.string.map_mark_color))

                            Glide.with(this)
                                .load(staticMapUri)
                                .into(iv_team_map)
                        }
                    })
                }
        }
    }

    private fun setupRecycler() {
        rv_participation.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = playoneAdapter
        }
    }
}