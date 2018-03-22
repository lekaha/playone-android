package com.playone.mobile.ui.playone

import android.os.Bundle
import android.view.View
import androidx.os.bundleOf
import com.playone.mobile.ext.DEFAULT_STR
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.R
import com.playone.mobile.ui.mapper.PlayoneMapper
import com.playone.mobile.ui.model.PlayoneListViewModel
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

    private var viewModel: PlayoneListViewModel? = null
    private val playoneId by lazy { arguments?.getString(PARAMETER_PLAYONE_ID) ?: DEFAULT_STR }

    override fun getLayoutId() = R.layout.fragment_playone_detail

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        println(playoneId)

        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        // get ViewModel from activity
        activity?.let {
            setupRecycler()
            viewModel?.load()
        }
    }

    private fun initViewModel() {

        activity?.let {
        }
    }

    private fun setupRecycler() {

//        rv_playone_list.apply {
//            layoutManager = LinearLayoutManager(activity)
//            adapter = playoneAdapter
//        }
    }
}