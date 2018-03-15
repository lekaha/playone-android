package com.playone.mobile.ui.playone

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import androidx.os.bundleOf
import com.playone.mobile.presentation.model.PlayoneView
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.R
import com.playone.mobile.ui.mapper.PlayoneMapper
import com.playone.mobile.ui.model.PlayoneListViewModel
import kotlinx.android.synthetic.main.fragment_playone_list.list_view
import javax.inject.Inject

class PlayoneListFragment : BaseInjectingFragment() {

    companion object {
        const val PARAMETER_USER_ID = "parameter playone list user id"

        fun newInstance(userId: String = "invalidString") = PlayoneListFragment().apply {
            arguments = bundleOf(PARAMETER_USER_ID to userId)
        }
    }

    @Inject lateinit var playoneAdapter: PlayoneAdapter
    var mapper: PlayoneMapper = PlayoneMapper()

    private var viewModel: PlayoneListViewModel? = null
    private val userId by lazy { arguments?.getString(PARAMETER_USER_ID) ?: "invalidString" }

    override fun getLayoutId() = R.layout.fragment_playone_list

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
            viewModel = ViewModelProviders.of(it).get(PlayoneListViewModel::class.java).apply {
                fetchListData().observe(this@PlayoneListFragment, Observer {
                    it?.takeIf(List<PlayoneView>::isNotEmpty)?.let {
                        playoneAdapter.update(mapper.mapToViewModels(it))
                        playoneAdapter.notifyDataSetChanged()
                    }
                })
            }
        }
    }

    private fun setupRecycler() {

        list_view.layoutManager = LinearLayoutManager(activity)
        list_view.adapter = playoneAdapter
    }
}