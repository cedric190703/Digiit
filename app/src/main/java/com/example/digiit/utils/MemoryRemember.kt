package com.example.digiit.utils

class MemoryRemember<T>(val callback: () -> T) {
    private var _value: T? = null
    private var locked = false

    val value: T
    get() {
        if (_value == null) _value = callback()
        return _value!!
    }

    fun free() {
        if (!locked) _value = null
    }

    fun lock() {
        locked = true
    }

    fun unlock() {
        locked = false
    }
}
