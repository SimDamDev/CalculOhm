package com.simdamdev.calculohm.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * UnitState - Inner class which holds the unit state information
 * @property r holds R unit state information
 * @property i holds I unit state information
 * @property u holds U unit state information
 * @property p holds P unit state information
 *
 */
class UnitState(
    var r: MutableState<String> = mutableStateOf(""),
    var i: MutableState<String> = mutableStateOf(""),
    var u: MutableState<String> = mutableStateOf(""),
    var p: MutableState<String> = mutableStateOf("")
)