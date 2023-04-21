package com.example.digiit.utils

class MemoryRemember<T>(val callback: () -> T) {
    private var _value: T? = null

    val value: T
    get() {
        if (_value == null) _value = callback()
        return _value!!
    }

    fun free() {
        _value = null
    }
}
