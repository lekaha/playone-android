package com.playone.mobile.common

import com.playone.mobile.ext.defaultStr
import org.junit.Test

const val INVALID_INT = -1

// https://stackoverflow.com/a/44470213

class MockClass {

    var abc by NotValidVar(INVALID_INT)
}

data class MockDataClass(var _abc: Int = INVALID_INT) {
    var abc by NotValidVar(if (_abc != INVALID_INT) _abc else INVALID_INT)
}

class NotValidValueTest {

    @Test
    fun test_invalid_value() {

        val mockClass = MockClass()
        mockClass.abc
    }

    @Test
    fun test_valid_value() {

        val mockClass = MockClass()
        mockClass.abc = 10
        mockClass.abc
    }

    @Test
    fun test_invalid_value_on_data_class() {

        val mockClass = MockClass()
        mockClass.abc
    }

    @Test
    fun test_valid_value_on_data_class() {

        val mockClass = MockClass()
        mockClass.abc = 10
        mockClass.abc
    }
}