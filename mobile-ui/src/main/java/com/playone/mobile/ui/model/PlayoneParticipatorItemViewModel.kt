package com.playone.mobile.ui.model

import android.support.annotation.IntegerRes
import com.playone.mobile.common.NotValidVar
import com.playone.mobile.ext.DEFAULT_INT

data class PlayoneParticipatorItemViewModel(
    private var _isParticipated: Boolean = false,
    @IntegerRes
    private var _iconParticipate: Int = DEFAULT_INT
) {

    companion object {

        const val DISPLAY_TYPE_PARTICIPATOR: Int = 5
    }

    var isParticipated = _isParticipated
    var iconParticipate by NotValidVar(_iconParticipate.takeIf { DEFAULT_INT != it } ?: DEFAULT_INT)
}