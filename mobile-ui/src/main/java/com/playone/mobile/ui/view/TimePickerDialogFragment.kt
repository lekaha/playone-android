package com.playone.mobile.ui.view

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import com.playone.mobile.ui.BaseDialogFragment
import java.util.Calendar
import java.util.Date

class TimePickerDialogFragment : BaseDialogFragment(), TimePickerDialog.OnTimeSetListener {

    companion object {
        private const val EXTRA_INITIAL_DATE = "EXTRA_INITIAL_DATE"

        fun newInstance(initialDate: Date?): TimePickerDialogFragment {
            val fragment = TimePickerDialogFragment()
            initialDate?.let {
                val args = Bundle()
                args.putSerializable(EXTRA_INITIAL_DATE, initialDate)
                fragment.arguments = args
            }
            return fragment
        }
    }

    var onTimeSetListener: ((date: Date) -> Unit) = {}

    private val calendar = Calendar.getInstance()

    private val initialDate: Date?
        get() = arguments?.getSerializable(EXTRA_INITIAL_DATE) as Date

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initialDate?.let { calendar.time = it }

        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        return TimePickerDialog(appCompatActivity!!, this, hourOfDay, minute, true)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        onTimeSetListener.invoke(calendar.time)
    }
}