package com.playone.mobile.ui.create

import android.animation.Animator
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.SeekBar
import androidx.core.os.bundleOf
import androidx.core.os.postDelayed
import com.dd.morphingbutton.MorphingButton
import com.playone.mobile.ext.DEFAULT_STR
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise
import com.playone.mobile.ext.toIntOrZero
import com.playone.mobile.presentation.createPlayone.CreatePlayoneContract
import com.playone.mobile.ui.BaseFragment
import com.playone.mobile.ui.R
import com.playone.mobile.ui.model.CreatePlayoneViewModel
import com.playone.mobile.ui.view.DatePickerDialogFragment
import com.playone.mobile.ui.view.TimePickerDialogFragment
import kotlinx.android.synthetic.main.fragment_playone_create_fields.btn_create
import kotlinx.android.synthetic.main.fragment_playone_create_fields.eTxtDate
import kotlinx.android.synthetic.main.fragment_playone_create_fields.eTxtMessage
import kotlinx.android.synthetic.main.fragment_playone_create_fields.eTxtName
import kotlinx.android.synthetic.main.fragment_playone_create_fields.eTxtPeople
import kotlinx.android.synthetic.main.fragment_playone_create_fields.eTxtTime
import kotlinx.android.synthetic.main.fragment_playone_create_fields.floatingActionButton
import kotlinx.android.synthetic.main.fragment_playone_create_fields.floatingActionButtonFromCamera
import kotlinx.android.synthetic.main.fragment_playone_create_fields.floatingActionButtonFromPhotos
import kotlinx.android.synthetic.main.fragment_playone_create_fields.seekBarLevel
import kotlinx.android.synthetic.main.fragment_playone_create_fields.textView5
import java.text.SimpleDateFormat
import java.util.Date

class CreatePlayoneFragment : BaseFragment() {

    companion object {

        private const val ERROR_LATLNG = -.0
        private const val ERROR_ADDRESS = DEFAULT_STR

        private const val EXTRA_LATITUDE = "EXTRA_LATITUDE"
        private const val EXTRA_LONGITUDE = "EXTRA_LONGITUDE"
        private const val EXTRA_ADDRESS = "EXTRA_ADDRESS"

        fun newInstance(lat: Double, lng: Double, address: String) =
            CreatePlayoneFragment().apply {
                arguments = bundleOf(
                    EXTRA_LATITUDE to lat,
                    EXTRA_LONGITUDE to lng,
                    EXTRA_ADDRESS to address
                )
            }
    }

    private var isFabMenuCollapse = true
    private val createLocationLat by lazy {
        arguments?.let { it.getDouble(EXTRA_LATITUDE) } ?: run {
            Error("Should pass location info")
            ERROR_LATLNG
        }
    }
    private val createLocationLng by lazy {
        arguments?.let { it.getDouble(EXTRA_LONGITUDE) } ?: run {
            Error("Should pass location info")
            ERROR_LATLNG
        }
    }
    private val createLocationAddr by lazy {
        arguments?.let { it.getString(EXTRA_ADDRESS) } ?: run {
            Error("Should pass location info")
            ERROR_ADDRESS
        }
    }
    private val playoneDate = Date()
    private lateinit var viewModel: CreatePlayoneViewModel

    override fun getLayoutId() = R.layout.fragment_playone_create_fields

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        appCompatActivity?.let { activity ->
            viewModel =
                ViewModelProviders.of(activity).get(CreatePlayoneViewModel::class.java).apply {
                    isProgressing.observe(this@CreatePlayoneFragment, Observer {
                        btn_create.blockTouch()
                    })

                    isPlayoneCreated.observe(this@CreatePlayoneFragment, Observer {
                        // TODO: Created, go back to list
                        activity.finish()
                    })
                }

            lifecycle::addObserver
        }
    }

    private fun setupViews() {
        // Floating action menu
        floatingActionButton.setOnClickListener {
            isFabMenuCollapse.ifTrue {
                expandFabMenu()
            } otherwise {
                collapseFabMenu()
            }
        }

        floatingActionButtonFromCamera.setOnClickListener {
            collapseFabMenu()
        }

        floatingActionButtonFromPhotos.setOnClickListener {
            collapseFabMenu()
        }

        eTxtDate.setText(SimpleDateFormat.getDateInstance().format(playoneDate).toString())
        eTxtDate.setOnClickListener {
            DatePickerDialogFragment.newInstance(playoneDate).apply {
                onDateSetListener = {
                    this@CreatePlayoneFragment.eTxtDate
                        .setText(SimpleDateFormat.getDateInstance().format(it).toString())
                    playoneDate.time = it.time
                }
            }.show(fragmentManager, null)
        }
        eTxtTime.setText(SimpleDateFormat("h:mm a").format(playoneDate).toString())
        eTxtTime.setOnClickListener {
            TimePickerDialogFragment.newInstance(playoneDate).apply {
                onTimeSetListener = {
                    this@CreatePlayoneFragment.eTxtTime
                        .setText(SimpleDateFormat("h:mm a").format(it).toString())
                    playoneDate.time = it.time
                }
            }.show(fragmentManager, null)
        }

        // Level seek bar
        textView5.text = seekBarLevel.progress.toString()
        seekBarLevel.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                fromUser.ifTrue { textView5.text = progress.toString() }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        // Create button
        val circle =
            MorphingButton.Params.create()
                .duration(0)
                .cornerRadius(100) // 56 dp
                .color(ContextCompat.getColor(appCompatActivity!!,
                                              R.color.colorPrimary)) // normal state color
                .colorPressed(ContextCompat.getColor(appCompatActivity!!,
                                                     R.color.colorPrimaryDark)) // pressed state color
                .text("Create")
        btn_create.morph(circle)

        Handler().postDelayed(100) {
            btn_create.visibility = View.VISIBLE
            btn_create.alpha = 0f
            btn_create.animate().alpha(1f).duration = 300
        }

        btn_create.setOnClickListener { showConfirmationDialog() }
    }

    private fun createPlayone(parameters: CreatePlayoneContract.CreatePlayoneParameters) {
        viewModel.createPlayone(parameters)
    }

    private fun showConfirmationDialog() {
        // TODO: Check fields value, empty or invalid

        val parameters = CreatePlayoneContract.CreatePlayoneParameters(
            eTxtName.text.toString(),
            eTxtMessage.text.toString(),
            playoneDate,
            CreatePlayoneContract.PlayonePlace(createLocationLat,
                                               createLocationLng,
                                               createLocationAddr),
            eTxtPeople.text.toString().toIntOrZero(),
            seekBarLevel.progress
        )

        appCompatActivity?.let {
            AlertDialog.Builder(it)
                .setTitle("Confirm to Create Playone")
                .setMessage(parameters.toString())
                .setPositiveButton("Confirm", { dialog, _ ->
                    createPlayone(parameters)
                    dialog.dismiss()
                })
                .setNegativeButton("Cancel", { dialog, _ ->
                    dialog.dismiss()
                })
                .setCancelable(false)
                .show()
        }

    }

    private fun expandFabMenu() {

        floatingActionButtonFromCamera.visibility = View.VISIBLE
        floatingActionButtonFromPhotos.visibility = View.VISIBLE

        floatingActionButton.animate().rotation(135f)

        floatingActionButtonFromCamera.animate()
            .translationY(appCompatActivity!!.resources.getDimension(R.dimen.standard_100))
            .rotation(0f)
        floatingActionButtonFromPhotos.animate()
            .translationY(appCompatActivity!!.resources.getDimension(R.dimen.standard_55))
            .rotation(0f).setListener(FabAnimatorListener(floatingActionButtonFromCamera,
                                                          floatingActionButtonFromPhotos))
    }

    private fun collapseFabMenu() {

        floatingActionButton.animate().rotation(0f)

        floatingActionButtonFromCamera.animate()
            .translationY(0f)
            .rotation(90f)
        floatingActionButtonFromPhotos.animate()
            .translationY(0f)
            .rotation(90f).setListener(FabAnimatorListener(floatingActionButtonFromCamera,
                                                           floatingActionButtonFromPhotos))
    }

    inner class FabAnimatorListener(private vararg val views: View) : Animator.AnimatorListener {

        override fun onAnimationRepeat(animation: Animator?) {}

        override fun onAnimationEnd(animation: Animator?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            if (!isFabMenuCollapse) {
                views.forEach {
                    it.visibility = View.GONE
                }
            }
            isFabMenuCollapse = !isFabMenuCollapse
        }

        override fun onAnimationCancel(animation: Animator?) {}

        override fun onAnimationStart(animation: Animator?) {}

    }
}