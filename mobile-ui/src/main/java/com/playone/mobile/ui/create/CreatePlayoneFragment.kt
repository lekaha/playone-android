package com.playone.mobile.ui.create

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.SeekBar
import androidx.os.postDelayed
import com.dd.morphingbutton.MorphingButton
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ext.otherwise
import com.playone.mobile.ui.BaseFragment
import com.playone.mobile.ui.R
import com.playone.mobile.ui.view.DatePickerDialogFragment
import com.playone.mobile.ui.view.TimePickerDialogFragment
import kotlinx.android.synthetic.main.fragment_playone_create_fields.btn_create
import kotlinx.android.synthetic.main.fragment_playone_create_fields.editText4
import kotlinx.android.synthetic.main.fragment_playone_create_fields.editText5
import kotlinx.android.synthetic.main.fragment_playone_create_fields.floatingActionButton
import kotlinx.android.synthetic.main.fragment_playone_create_fields.floatingActionButtonFromCamera
import kotlinx.android.synthetic.main.fragment_playone_create_fields.floatingActionButtonFromPhotos
import kotlinx.android.synthetic.main.fragment_playone_create_fields.seekBar
import kotlinx.android.synthetic.main.fragment_playone_create_fields.textView5
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreatePlayoneFragment : BaseFragment() {

    companion object {

        fun newInstance() = CreatePlayoneFragment()
    }

    var isFabMenuCollapse = true

    override fun getLayoutId() = R.layout.fragment_playone_create_fields

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
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

        val now = Date()
        editText4.setText(SimpleDateFormat.getDateInstance().format(now).toString())
        editText4.setOnClickListener {
            DatePickerDialogFragment.newInstance(now).apply {
                onDateSetListener = {
                    this@CreatePlayoneFragment.editText4
                        .setText(SimpleDateFormat.getDateInstance().format(it).toString())
                }
            }.show(fragmentManager, null)
        }
        editText5.setText(SimpleDateFormat("h:mm a").format(now).toString())
        editText5.setOnClickListener {
            TimePickerDialogFragment.newInstance(now).apply {
                onTimeSetListener = {
                    this@CreatePlayoneFragment.editText5
                        .setText(SimpleDateFormat("h:mm a").format(it).toString())
                }
            }.show(fragmentManager, null)
        }

        // Level seek bar
        textView5.text = seekBar.progress.toString()
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
                .duration(500)
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

        override fun onAnimationRepeat(animation: Animator?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onAnimationEnd(animation: Animator?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            if (!isFabMenuCollapse) {
                views.forEach {
                    it.visibility = View.GONE
                }
            }
            isFabMenuCollapse = !isFabMenuCollapse
        }

        override fun onAnimationCancel(animation: Animator?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onAnimationStart(animation: Animator?) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}