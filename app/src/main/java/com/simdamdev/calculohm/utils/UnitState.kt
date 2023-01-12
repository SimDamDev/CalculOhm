package com.simdamdev.calculohm.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * UnitState - Inner class which holds the unit state information
 * @property R holds R unit state information
 * @property I holds I unit state information
 * @property U holds U unit state information
 * @property P holds P unit state information
 *
 */
class UnitState(
    var R: MutableState<String> = mutableStateOf(""),
    var I: MutableState<String> = mutableStateOf(""),
    var U: MutableState<String> = mutableStateOf(""),
    var P: MutableState<String> = mutableStateOf("")
)