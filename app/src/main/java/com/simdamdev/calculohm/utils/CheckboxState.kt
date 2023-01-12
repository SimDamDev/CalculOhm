package com.simdamdev.calculohm.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * CheckboxState - Inner class which holds the checkbox state information
 * @property R holds R checkbox state information
 * @property I holds I checkbox state information
 * @property U holds U checkbox state information
 * @property P holds P checkbox state information
 *
 */
class CheckboxState(
    var R: MutableState<Boolean> = mutableStateOf(false),
    var I: MutableState<Boolean> = mutableStateOf(false),
    var U: MutableState<Boolean> = mutableStateOf(false),
    var P: MutableState<Boolean> = mutableStateOf(false)
){
    // List of all checkbox states
    val checkboxList : List<MutableState<Boolean>> = listOf(R,I,U,P)
}