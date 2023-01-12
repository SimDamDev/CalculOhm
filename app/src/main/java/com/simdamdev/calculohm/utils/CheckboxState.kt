package com.simdamdev.calculohm.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * CheckboxState - Inner class which holds the checkbox state information
 * @property r holds R checkbox state information
 * @property i holds I checkbox state information
 * @property u holds U checkbox state information
 * @property p holds P checkbox state information
 *
 */
class CheckboxState(
    var r: MutableState<Boolean> = mutableStateOf(false),
    var i: MutableState<Boolean> = mutableStateOf(false),
    var u: MutableState<Boolean> = mutableStateOf(false),
    var p: MutableState<Boolean> = mutableStateOf(false)
){
    // List of all checkbox states
    val checkboxList : List<MutableState<Boolean>> = listOf(r,i,u,p)
}