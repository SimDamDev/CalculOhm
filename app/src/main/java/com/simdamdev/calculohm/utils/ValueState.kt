package com.simdamdev.calculohm.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * ValueState - Inner class which holds the value state information
 * @property r holds R value state information
 * @property i holds I value state information
 * @property u holds U value state information
 * @property p holds P value state information
 *
 */
class ValueState(
    var r: MutableState<String> = mutableStateOf(""),
    var i: MutableState<String> = mutableStateOf(""),
    var u: MutableState<String> = mutableStateOf(""),
    var p: MutableState<String> = mutableStateOf(""),
    var rTransformed: MutableState<Double> = mutableStateOf(0.0),
    var iTransformed: MutableState<Double> = mutableStateOf(0.0),
    var uTransformed: MutableState<Double> = mutableStateOf(0.0),
    var pTransformed: MutableState<Double> = mutableStateOf(0.0),
    val unit : UnitState
){
    fun convertR(): Double {
        if (r.value == ""){
            r.value = 0.0.toString()
        }
        rTransformed.value = r.value.toDouble() * unitToFactor(unit.r.value)
        return rTransformed.value
    }

    fun convertI(): Double {
        if (i.value == ""){
            i.value = 0.0.toString()
        }
        iTransformed.value = i.value.toDouble() * unitToFactor(unit.i.value)
        return iTransformed.value
    }

    fun convertU(): Double {
        if (u.value == ""){
            u.value = 0.0.toString()
        }
        uTransformed.value = u.value.toDouble() * unitToFactor(unit.u.value)
        return uTransformed.value
    }

    fun convertP(): Double {
        if (p.value == ""){
            p.value = 0.0.toString()
        }
        pTransformed.value = p.value.toDouble() * unitToFactor(unit.p.value)
        return pTransformed.value
    }
}