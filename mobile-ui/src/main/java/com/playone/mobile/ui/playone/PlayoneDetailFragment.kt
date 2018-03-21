package com.playone.mobile.ui.playone

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import androidx.os.bundleOf
import com.playone.mobile.ext.DEFAULT_INT
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.R
import com.playone.mobile.ui.mapper.PlayoneMapper
import com.playone.mobile.ui.model.PlayoneListViewModel
import kotlinx.android.synthetic.main.fragment_playone_list.rv_playone_list
import javax.inject.Inject

class PlayoneDetailFragment : BaseInjectingFragment() {

    companion object {
        const val PARAMETER_PLAYONE_ID = "parameter playone id"

        fun newInstance(playoneId: Int = DEFAULT_INT) = PlayoneDetailFragment().apply {
            arguments = bundleOf(PARAMETER_PLAYONE_ID to userId)
        }
    }

    @Inject lateinit var playoneAdapter: PlayoneAdapter
    @Inject lateinit var mapper: PlayoneMapper

    private var viewModel: PlayoneListViewModel? = null
    private val userId by lazy { arguments?.getInt(PARAMETER_PLAYONE_ID) ?: DEFAULT_INT }

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
            viewModel?.load()
        }
    }

    private fun initViewModel() {

        activity?.let {
        }
    }

    private fun setupRecycler() {

        rv_playone_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = playoneAdapter
        }
    }
}