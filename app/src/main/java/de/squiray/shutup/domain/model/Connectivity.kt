package de.squiray.shutup.domain.model

interface Connectivity {

    fun turnOn()

    fun turnOff()

    fun isOn(): Boolean
}
