package com.playone.mobile.ui.playone

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import androidx.os.bundleOf
import com.playone.mobile.ext.defaultInt
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.R
import com.playone.mobile.ui.mapper.PlayoneMapper
import com.playone.mobile.ui.model.PlayoneListViewModel
import kotlinx.android.synthetic.main.fragment_playone_list.rv_playone_list
import javax.inject.Inject

class PlayoneDetailFragment : BaseInjectingFragment() {

    companion object {
        const val PARAMETER_PLAYONE_ID = "parameter playone id"

        fun newInstance(playoneId: Int = defaultInt) = PlayoneDetailFragment().apply {
            arguments = bundleOf(PARAMETER_PLAYONE_ID to userId)
        }
    }

    // TODO(jieyi): 2018/03/18 Inject the objects.
    //    @Inject
    lateinit var playoneAdapter: PlayoneAdapter
    @Inject lateinit var mapper: PlayoneMapper

    private var viewModel: PlayoneListViewModel? = null
    private val userId by lazy { arguments?.getInt(PARAMETER_PLAYONE_ID) ?: defaultInt }

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