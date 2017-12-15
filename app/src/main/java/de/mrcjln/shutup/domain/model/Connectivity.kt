package de.mrcjln.shutup.domain.model

interface Connectivity {

    fun turnOn()

    fun turnOff()

    fun isOn(): Boolean
}
