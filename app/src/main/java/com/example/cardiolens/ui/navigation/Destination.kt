package com.example.cardiolens.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Destination : NavKey {

    @Serializable
    data object Cardio : Destination
    @Serializable
    data object Stats : Destination
    @Serializable
    data object Settings : Destination

    companion object {
        val entries = listOf(Cardio, Stats, Settings)
    }
}