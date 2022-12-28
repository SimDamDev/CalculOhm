package com.example.calculohm.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import kotlin.math.sqrt


class ColorList(
    val col1: Color,
    val col2: Color,
    val col3: Color,
    val col4: Color,
)

class State(
    val checkbox: CheckboxState = CheckboxState(),
    val value: ValueState = ValueState(),
    val unit: UnitState = UnitState()
) {
    class CheckboxState(
        var R: MutableState<Boolean> = mutableStateOf(false),
        var I: MutableState<Boolean> = mutableStateOf(false),
        var U: MutableState<Boolean> = mutableStateOf(false),
        var P: MutableState<Boolean> = mutableStateOf(false)
    )

    class ValueState(
        var R: MutableState<String> = mutableStateOf(""),
        var I: MutableState<String> = mutableStateOf(""),
        var U: MutableState<String> = mutableStateOf(""),
        var P: MutableState<String> = mutableStateOf("")
    )

    class UnitState(
        var R: MutableState<String> = mutableStateOf(""),
        var I: MutableState<String> = mutableStateOf(""),
        var U: MutableState<String> = mutableStateOf(""),
        var P: MutableState<String> = mutableStateOf("")
    )
}


fun exactlyTwoChecked(state: State): Boolean {
    var count = 0
    if (state.checkbox.R.value) count++
    if (state.checkbox.I.value) count++
    if (state.checkbox.U.value) count++
    if (state.checkbox.P.value) count++
    return count == 2
}


fun unitToFactor(unit: String): Double {
    return when (unit) {
        "mΩ" -> 0.001
        "Ω" -> 1.0
        "kΩ" -> 1000.0
        "MΩ" -> 1000000.0
        "GΩ" -> 1000000000.0
        "mV" -> 0.001
        "V" -> 1.0
        "kV" -> 1000.0
        "MV" -> 1000000.0
        "GV" -> 1000000000.0
        "mA" -> 0.001
        "A" -> 1.0
        "kA" -> 1000.0
        "MA" -> 1000000.0
        "GA" -> 1000000000.0
        "mW" -> 0.001
        "W" -> 1.0
        "kW" -> 1000.0
        "MW" -> 1000000.0
        "GW" -> 1000000000.0
        else -> 1.0
    }
}

fun calculate(state: State) {
    val rFactor = unitToFactor(state.unit.R.value)
    val iFactor = unitToFactor(state.unit.I.value)
    val uFactor = unitToFactor(state.unit.U.value)
    val pFactor = unitToFactor(state.unit.P.value)
    val rTemp = state.value.R.value.toDoubleOrNull()?.times(rFactor)
    val iTemp = state.value.I.value.toDoubleOrNull()?.times(iFactor)
    val uTemp = state.value.U.value.toDoubleOrNull()?.times(uFactor)
    val pTemp = state.value.P.value.toDoubleOrNull()?.times(pFactor)

    // Calculate unknown variables using Ohm's law
    if (state.checkbox.R.value && state.checkbox.I.value) {
        if (rTemp != null && iTemp != null) {
            val u = rTemp * iTemp
            val p = u * iTemp
            state.value.U.value = u.toString()
            state.value.P.value = p.toString()
        }
    } else if (state.checkbox.R.value && state.checkbox.U.value) {
        if (rTemp != null && uTemp != null) {
            val i = uTemp / rTemp
            val p = uTemp * i
            state.value.I.value = i.toString()
            state.value.P.value = p.toString()
        }
    } else if (state.checkbox.R.value && state.checkbox.P.value) {
        if (rTemp != null && pTemp != null) {
            val i = sqrt(pTemp / rTemp)
            val u = rTemp * i
            state.value.I.value = i.toString()
            state.value.U.value = u.toString()
        }
    } else if (state.checkbox.I.value && state.checkbox.U.value) {
        if (iTemp != null && uTemp != null) {
            val p = iTemp * uTemp
            val r = uTemp / iTemp
            state.value.P.value = p.toString()
            state.value.R.value = r.toString()
        }
    } else if (state.checkbox.I.value && state.checkbox.P.value) {
        if (iTemp != null && pTemp != null) {
            val u = pTemp / iTemp
            val r = u / iTemp
            state.value.U.value = u.toString()
            state.value.R.value = r.toString()
        }
    } else if (state.checkbox.U.value && state.checkbox.P.value) {
        if (uTemp != null && pTemp != null) {
            val i = pTemp / uTemp
            val r = uTemp / i
            state.value.I.value = i.toString()
            state.value.R.value = r.toString()
        }
    }
}