package com.playone.mobile.ui.playone

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Rect
import android.os.Bundle
import android.support.transition.Fade
import android.support.transition.Slide
import android.support.transition.Transition
import android.support.transition.TransitionSet
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.os.bundleOf
import com.playone.mobile.ext.DEFAULT_STR
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise
import com.playone.mobile.presentation.model.PlayoneView
import com.playone.mobile.ui.BaseInjectingFragment
import com.playone.mobile.ui.Navigator
import com.playone.mobile.ui.R
import com.playone.mobile.ui.ext.observe
import com.playone.mobile.ui.mapper.PlayoneMapper
import com.playone.mobile.ui.model.PlayoneListItemViewModel
import com.playone.mobile.ui.model.PlayoneListViewModel
import com.playone.mobile.ui.model.PlayoneViewModel
import com.playone.mobile.ui.view.ExplodeFadeOut
import com.playone.mobile.ui.view.SlideFadeOut
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
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
    @Inject lateinit var navigator: Navigator

    private val viewModel by lazy {
        activity?.let {
            ViewModelProviders.of(it).get(PlayoneListViewModel::class.java)
        }
    }

    private val playoneViewModel by lazy {
        activity?.let {
            ViewModelProviders.of(it).get(PlayoneViewModel::class.java)
        }
    }

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
        }
    }

    override fun onResume() {
        super.onResume()

        playoneViewModel?.changeMode(PlayoneViewModel.CONTENT_MODE_LIST)
    }

    private fun getListFragmentExitTransition(itemView: View, duration: Long = 300L): Transition =
        ExplodeFadeOut().apply {
            val epicCenterRect = Rect().apply {

                //itemView is the full-width inbox item's view
                itemView.getGlobalVisibleRect(this)

                // Set Epic center to a imaginary horizontal full width line under the clicked item,
                // so the explosion happens vertically away from it
                top = bottom
            }

            epicenterCallback = object : Transition.EpicenterCallback() {
                override fun onGetEpicenter(transition: Transition) = epicCenterRect
            }

            setDuration(duration)
        }

    private fun getDetailFragmentExitTransition(duration: Long = 300L): Transition =
        TransitionSet().apply {
            addTransition(Slide(Gravity.BOTTOM))
            addTransition(Fade().apply {
                startDelay = duration / 2
            })

            setDuration(duration)
        }

    private fun getDetailFragmentReturnTransition(duration: Long = 300L): Transition =
        SlideFadeOut().apply {
            setDuration(duration)
        }

    fun navigateToDetail(view: View, model: PlayoneListItemViewModel) {

        appCompatActivity?.let {
            navigator.navigateToFragment(it) {
                val fragment = PlayoneDetailFragment.newInstance(mapper.mapToView(model))
                replace(R.id.list_content,
                        fragment,
                        PlayoneDetailFragment::class.java.simpleName)

                exitTransition = getListFragmentExitTransition(view)
                reenterTransition = exitTransition

                fragment.enterTransition = getDetailFragmentExitTransition()
                fragment.returnTransition = getDetailFragmentReturnTransition()

                allowEnterTransitionOverlap = true
                allowReturnTransitionOverlap = true

                addToBackStack(null)
            }

            playoneViewModel?.changeMode(PlayoneViewModel.CONTENT_MODE_DETAIL)
        }
    }

    private fun initViewModel() {

        viewModel?.apply {
            fetchListData().observe(this@PlayoneListFragment, Observer {
                it?.takeIf(List<PlayoneView>::isNotEmpty)?.let {
                    playoneAdapter.update(mapper.mapToViewModels(it) { view, model ->
                        (view is View).ifTrue {
                            navigateToDetail(view as View, model)
                        } otherwise {
                            throw IllegalArgumentException("Cannot solve the instance as View")
                        }
                    })
                    playoneAdapter.notifyDataSetChanged()
                }
            })

            observeProgress(this@PlayoneListFragment, Observer {
                it?.let {
                    swipeRefreshLayout.isRefreshing = it
                }
            })

            listFilterString.observe(this@PlayoneListFragment) { query ->

                filterByName(query.orEmpty()) {
                    mapper.mapToViewModels(it) { view, model ->
                        (view is View).ifTrue {
                            navigateToDetail(view as View, model)
                        } otherwise {
                            throw IllegalArgumentException("Cannot solve the instance as View")
                        }
                    }.also {
                        playoneAdapter.update(it)
                    }
                }
            }

            (playoneList.value?.isEmpty() != false).ifTrue { load() }
        }
    }

    private fun setupRecycler() {

        rv_playone_list.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = SlideInUpAnimator(OvershootInterpolator())
            adapter = AlphaInAnimationAdapter(playoneAdapter).apply {
                setInterpolator(OvershootInterpolator())
            }

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