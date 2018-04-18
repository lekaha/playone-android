package com.playone.mobile.ui.create

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.SeekBar
import com.dd.morphingbutton.MorphingButton
import com.playone.mobile.ext.ifTrue
import com.playone.mobile.ui.BaseFragment
import com.playone.mobile.ui.R
import kotlinx.android.synthetic.main.fragment_playone_create_fields.btn_create
import kotlinx.android.synthetic.main.fragment_playone_create_fields.seekBar
import kotlinx.android.synthetic.main.fragment_playone_create_fields.textView5

class CreatePlayoneFragment : BaseFragment() {

    companion object {

        fun newInstance() = CreatePlayoneFragment()
    }

    override fun getLayoutId() = R.layout.fragment_playone_create_fields

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
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

        val circle =
            MorphingButton.Params.create()
                .duration(500)
                .cornerRadius(100) // 56 dp
                .color(ContextCompat.getColor(appCompatActivity!!, R.color.colorPrimary)) // normal state color
                .colorPressed(ContextCompat.getColor(appCompatActivity!!, R.color.colorPrimaryDark)) // pressed state color
                .text("Create")
        btn_create.morph(circle)
        btn_create.visibility = View.VISIBLE
    }

}