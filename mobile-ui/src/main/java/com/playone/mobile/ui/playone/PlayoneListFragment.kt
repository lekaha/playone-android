package com.playone.mobile.ui.playone

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import androidx.core.os.bundleOf
import com.playone.mobile.ext.DEFAULT_STR
import com.playone.mobile.presentation.model.PlayoneView
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.R
import com.playone.mobile.ui.mapper.PlayoneMapper
import com.playone.mobile.ui.model.PlayoneListViewModel
import kotlinx.android.synthetic.main.fragment_playone_list.rv_playone_list
import kotlinx.android.synthetic.main.fragment_playone_list.swipeRefreshLayout
import javax.inject.Inject

class PlayoneListFragment : BaseInjectingFragment() {

    companion object {
        const val PARAMETER_USER_ID = "parameter playone list user id"

        fun newInstance(userId: String = DEFAULT_STR) = PlayoneListFragment().apply {
            arguments = bundleOf(PARAMETER_USER_ID to userId)
        }
    }

    @Inject lateinit var playoneAdapter: PlayoneAdapter
    @Inject lateinit var mapper: PlayoneMapper

    private val viewModel by lazy { activity?.let {
        ViewModelProviders.of(it).get(PlayoneListViewModel::class.java)
    } }
    private val userId by lazy { arguments?.getString(PARAMETER_USER_ID) ?: DEFAULT_STR }

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
        viewModel?.apply {
            fetchListData().observe(this@PlayoneListFragment, Observer {
                it?.takeIf(List<PlayoneView>::isNotEmpty)?.let {
                    playoneAdapter.update(mapper.mapToViewModels(it))
                    playoneAdapter.notifyDataSetChanged()
                }
            })

            observeProgress(this@PlayoneListFragment, Observer {
                it?.let {
                    swipeRefreshLayout.isRefreshing = it
                }
            })
        }
    }

    private fun setupRecycler() {

        rv_playone_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = playoneAdapter

            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (layoutManager is LinearLayoutManager) {
                        swipeRefreshLayout.isEnabled = (layoutManager as LinearLayoutManager)
                            .findFirstCompletelyVisibleItemPosition() != 0
                    }
                }
            })
        }
    }
}