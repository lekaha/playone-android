package com.playone.mobile.ui.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import com.playone.mobile.ui.BaseDialogFragment
import java.util.Calendar
import java.util.Date

class DatePickerDialogFragment : BaseDialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        private const val EXTRA_INITIAL_DATE = "EXTRA_INITIAL_DATE"

        fun newInstance(initialDate: Date?): DatePickerDialogFragment {
            val fragment = DatePickerDialogFragment()
            initialDate?.let {
                val args = Bundle()
                args.putSerializable(EXTRA_INITIAL_DATE, initialDate)
                fragment.arguments = args
            }
            return fragment
        }
    }

    var onDateSetListener: ((date: Date) -> Unit) = {}

    private val calendar = Calendar.getInstance()

    private val initialDate: Date?
        get() = arguments?.getSerializable(EXTRA_INITIAL_DATE) as Date

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initialDate?.let { calendar.time = it }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(appCompatActivity!!, this, year, month, dayOfMonth)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        calendar.set(year, month, day)
        onDateSetListener.invoke(calendar.time)
    }
}