package com.playone.mobile.ui.browse

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.playone.mobile.presentation.model.BufferooView
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.R
import com.playone.mobile.ui.R.id.recycler_browse
import com.playone.mobile.ui.mapper.BufferooMapper
import com.playone.mobile.ui.model.BrowseViewModel
import com.playone.mobile.ui.model.BrowseViewModelFactory
import kotlinx.android.synthetic.main.fragment_browse.*
import javax.inject.Inject

class BrowseFragment : BaseInjectingFragment() {

    @Inject lateinit var browseAdapter: BrowseAdapter
    @Inject lateinit var mapper: BufferooMapper
    @Inject lateinit var browseViewModelFactory: BrowseViewModelFactory

    private var viewModel: BrowseViewModel? = null

    companion object {
        fun newInstance() = BrowseFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBrowseRecycler()

        viewModel?.load()
    }

    @LayoutRes
    override fun getLayoutId() = R.layout.fragment_browse

    private fun showProgress() {
        progress.visibility = View.GONE
    }

    private fun hideProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun showBufferoos(bufferoos: List<BufferooView>) {
        browseAdapter.update(mapper.mapToViewModels(bufferoos))
        browseAdapter.notifyDataSetChanged()
        recycler_browse.visibility = View.VISIBLE
    }

    private fun hideBufferoos() {
        recycler_browse.visibility = View.VISIBLE
    }

    private fun showErrorState() {

    }

    private fun hideErrorState() {

    }

    private fun showEmptyState() {

    }

    private fun hideEmptyState() {

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, browseViewModelFactory)
            .get(BrowseViewModel::class.java)

        viewModel?.isProgressing()?.observe(this, Observer {
            it?.takeIf { it }?.apply { showProgress() } ?: hideProgress()
        })

        viewModel?.occurredError()?.observe(this, Observer { _ ->
            showErrorState()
        })

        viewModel?.fetchedData()?.observe(this, Observer { data ->
            hideErrorState()

            data?.let {
                if (data.isNotEmpty()) {
                    hideEmptyState()
                    showBufferoos(data)
                }
                else {
                    showEmptyState()
                    hideBufferoos()
                }
            } ?: run {
                showEmptyState()
                hideBufferoos()
            }
        })

        viewModel?.let { lifecycle.addObserver(it) }
    }

    private fun setupBrowseRecycler() {
        recycler_browse.layoutManager = LinearLayoutManager(activity)
        recycler_browse.adapter = browseAdapter
    }
}