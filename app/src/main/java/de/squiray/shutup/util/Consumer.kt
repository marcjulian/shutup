package de.squiray.shutup.util

interface Consumer<in T> {

    fun accept(value: T)

}
