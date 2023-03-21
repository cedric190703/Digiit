package com.example.digiit.utils

import androidx.compose.runtime.MutableState


class ObservableMutableState<T>(initial: T, val changed: (value: T) -> Unit): MutableState<T> {
    override var value: T = initial
        set(value) {
            field = value
            changed(value)
        }

    override operator fun component1(): T = value
    override operator fun component2(): (T) -> Unit = { value = it }
}
