package com.playone.mobile.ui.model

data class PlayoneListItemViewModel(val name: String,
                                    val description: String,
                                    val joinedNumber: Int,
                                    val totalNumber: Int) {

    companion object {
        const val DISPLAY_TYPE_PLAYONE: Int = 4
    }
}