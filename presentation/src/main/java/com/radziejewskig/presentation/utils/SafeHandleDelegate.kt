package com.radziejewskig.presentation.utils

import androidx.lifecycle.SavedStateHandle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Property delegate for auto storing/restoring SavedStateHandle values.
 * Returns default value if there is no any.
 */
internal class SafeHandleDelegate<T>(
    private val handle: SavedStateHandle,
    private val key: String,
    private val defaultValue: T
) : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return handle[key] ?: defaultValue
    }
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        handle[key] = value
    }
}
