package com.playone.mobile.ui.playone

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import androidx.os.bundleOf
import com.playone.mobile.ext.DEFAULT_STR
import com.playone.mobile.presentation.model.PlayoneView
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.R
import com.playone.mobile.ui.mapper.PlayoneMapper
import com.playone.mobile.ui.model.PlayoneListViewModel
import kotlinx.android.synthetic.main.fragment_playone_list.rv_playone_list
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

    private var viewModel: PlayoneListViewModel? = null
    private val userId by lazy { arguments?.getString(PARAMETER_USER_ID) ?: DEFAULT_STR }

    override fun getLayoutId() = R.layout.fragment_playone_list

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        playoneAdapter.register(this)
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

    fun navigateToDetail(playoneId: String) {

        val keyOfFragment = PlayoneDetailFragment::class.java.simpleName

        (activity as PlayoneActivity).gotoFragment(keyOfFragment,
                                                   hashMapOf(keyOfFragment to playoneId))
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

        rv_playone_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = playoneAdapter
        }
    }
}