package com.playone.mobile.ui.playone

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import androidx.os.bundleOf
import com.playone.mobile.ext.DEFAULT_STR
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.R
import com.playone.mobile.ui.mapper.PlayoneMapper
import com.playone.mobile.ui.model.PlayoneDetailViewModel
import com.playone.mobile.ui.model.PlayoneParticipatorItemViewModel
import com.playone.mobile.ui.model.PlayoneParticipatorItemViewModel.Companion.DISPLAY_TYPE_PARTICIPATOR
import com.playone.mobile.ui.view.recycler.DisplayableItem.Companion.toDisplayableItem
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
                        }
                    })
                }
        }

        playoneAdapter.update(listOf(toDisplayableItem(PlayoneParticipatorItemViewModel(),
                                                       DISPLAY_TYPE_PARTICIPATOR),
                                     toDisplayableItem(PlayoneParticipatorItemViewModel(),
                                                       DISPLAY_TYPE_PARTICIPATOR)))
        playoneAdapter.notifyDataSetChanged()
    }

    private fun setupRecycler() {

//        rv_playone_list.apply {
//            layoutManager = LinearLayoutManager(activity)
//            adapter = playoneAdapter
//        }
    }
}