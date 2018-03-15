package com.playone.mobile.ui.playone

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.PlayoneListViewModel
import kotlinx.android.synthetic.main.fragment_browse.recycler_browse
import javax.inject.Inject

class PlayoneListFragment : BaseInjectingFragment() {

    @Inject lateinit var playoneAdapter: PlayoneAdapter

    private lateinit var viewModel: PlayoneListViewModel

    override fun getLayoutId() = R.layout.fragment_playone_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        // get ViewModel from activity
        activity?.let {
            setupRecycler()
            viewModel = ViewModelProviders.of(it).get(PlayoneListViewModel::class.java)
            viewModel.load()
        }
    }

    private fun setupRecycler() {

        recycler_browse.layoutManager = LinearLayoutManager(activity)
        recycler_browse.adapter = playoneAdapter
    }
}