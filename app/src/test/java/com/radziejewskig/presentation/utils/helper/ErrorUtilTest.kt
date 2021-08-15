package com.radziejewskig.presentation.utils.helper

import com.radziejewskig.presentation.R
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorUtilTest {

    @Test
    fun `unhandled throwable returns an_unknown_error_occurred string resource`() {
        val throwable = Throwable("TEST Unknown throwable")
        val result = ErrorUtil.getStringResForException(throwable)
        assertThat(result).isEqualTo(R.string.an_unknown_error_occurred)
    }

    @Test
    fun `null throwable returns an_unknown_error_occurred string resource`() {
        val throwable = null
        val result = ErrorUtil.getStringResForException(throwable)
        assertThat(result).isEqualTo(R.string.an_unknown_error_occurred)
    }

    @Test
    fun `socketTimeoutException returns error_check_internet_connection string resource`() {
        val throwable = SocketTimeoutException()
        val result = ErrorUtil.getStringResForException(throwable)
        assertThat(result).isEqualTo(R.string.error_check_internet_connection)
    }

    @Test
    fun `unknownHostException returns error_check_internet_connection string resource`() {
        val throwable = UnknownHostException()
        val result = ErrorUtil.getStringResForException(throwable)
        assertThat(result).isEqualTo(R.string.error_check_internet_connection)
    }

}
