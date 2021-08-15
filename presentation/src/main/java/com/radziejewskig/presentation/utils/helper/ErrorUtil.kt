package com.radziejewskig.presentation.utils.helper

import com.radziejewskig.presentation.R
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorUtil {
    fun getStringResForException(e: Throwable?): Int {
        return when(e) {
            is SocketTimeoutException,
            is UnknownHostException -> R.string.error_check_internet_connection
            else -> R.string.an_unknown_error_occurred
        }
    }
}
