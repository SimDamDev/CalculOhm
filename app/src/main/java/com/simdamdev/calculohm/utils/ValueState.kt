package com.simdamdev.calculohm.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * ValueState - Inner class which holds the value state information
 * @property R holds R value state information
 * @property I holds I value state information
 * @property U holds U value state information
 * @property P holds P value state information
 *
 */
class ValueState(
    var R: MutableState<String> = mutableStateOf(""),
    var I: MutableState<String> = mutableStateOf(""),
    var U: MutableState<String> = mutableStateOf(""),
    var P: MutableState<String> = mutableStateOf(""),
    var R_transformed: MutableState<Double> = mutableStateOf(0.0),
    var I_transformed: MutableState<Double> = mutableStateOf(0.0),
    var U_transformed: MutableState<Double> = mutableStateOf(0.0),
    var P_transformed: MutableState<Double> = mutableStateOf(0.0),
    val unit : UnitState
){
    fun convertR(): Double {
        if (R.value == ""){
            R.value = 0.0.toString()
        }
        R_transformed.value = R.value.toDouble() * unitToFactor(unit.R.value)
        return R_transformed.value
    }

    fun convertI(): Double {
        if (I.value == ""){
            I.value = 0.0.toString()
        }
        I_transformed.value = I.value.toDouble() * unitToFactor(unit.I.value)
        return I_transformed.value
    }

    fun convertU(): Double {
        if (U.value == ""){
            U.value = 0.0.toString()
        }
        U_transformed.value = U.value.toDouble() * unitToFactor(unit.U.value)
        return U_transformed.value
    }

    fun convertP(): Double {
        if (P.value == ""){
            P.value = 0.0.toString()
        }
        P_transformed.value = P.value.toDouble() * unitToFactor(unit.P.value)
        return P_transformed.value
    }
}