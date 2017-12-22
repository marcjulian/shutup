package de.squiray.shutup.presentation.ui

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Activity(
        val layout: Int = -1,
        val menu: Int = -1
)
