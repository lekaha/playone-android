package com.playone.mobile.common

import com.playone.mobile.common.exception.InvalidPropertyValueException
import org.junit.Test

const val INVALID_INT = -1

// https://stackoverflow.com/a/44470213

class MockClass {

    var abc by NotValidVar(INVALID_INT)
}

data class MockDataClass(var _abc: Int = INVALID_INT) {
    var abc by NotValidVar(_abc)
}

class NotValidValueTest {

    @Test(expected = InvalidPropertyValueException::class)
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

    @Test(expected = InvalidPropertyValueException::class)
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