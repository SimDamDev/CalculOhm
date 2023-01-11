package com.simdamdev.calculohm.utils

import android.util.Log
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
    // holds checkbox state information
    val checkbox: CheckboxState = CheckboxState(),
    // holds value state information
    val value: ValueState = ValueState(),
    // holds unit state information
    val unit: UnitState = UnitState()
) {
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
        var P: MutableState<String> = mutableStateOf("")
    )

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
}


/**
 * Units : enumeration class that stores the factors of various SI prefixes.
 * The available prefixes are:
 * - NANO : prefix for 0.000000001
 * - MICRO : prefix for 0.000001
 * - MILLI : prefix for 0.001
 * - UNITY : prefix for 1.0 (default)
 * - KILO : prefix for 1000.0
 * - MEGA : prefix for 1000000.0
 * - GIGA : prefix for 1000000000.0
 * The companion object provides two methods:
 *  - fromPrefix(prefix: String): Units  that allows to find the corresponding
 *          Units instance given a string 'prefix'
 *  - fromFactor(number: Double): String that allows to find the corresponding
 *          prefix given a number
 */
enum class Units(val factor: Double) {
    NANO(0.000000001),
    MICRO(0.000001),
    MILLI(0.001),
    UNITY(1.0),
    KILO(1000.0),
    MEGA(1000000.0),
    GIGA(1000000000.0);

    companion object {
        fun fromPrefix(prefix: String): Units {
            return when (prefix) {
                "n" -> NANO
                "µ" -> MICRO
                "m" -> MILLI
                "k" -> KILO
                "M" -> MEGA
                "G" -> GIGA
                else -> UNITY
            }
        }

        fun fromFactor(number: Double): String {
            return when {
                number < 0.000001 -> "n"
                number < 0.001 -> "µ"
                number < 1.0 -> "m"
                number > 999999999.99 -> "G"
                number > 999999.99 -> "M"
                number > 999.99 -> "k"
                else -> ""
            }
        }
    }
}

/**
 * exactlyTwoTrue : check if exactly two input booleans are true
 * @param booleans : a list of booleans to check
 * @return true if exactly two booleans are true, false otherwise
 */
fun exactlyTwoTrue(vararg booleans: Boolean): Boolean {
    return booleans.count { it } == 2
}

/**
 * exactlyTwoChecked : check if exactly two checkboxes are checked in the state object
 * @param state : a state object
 * @return true if exactly two checkboxes are true, false otherwise.
 *         return false if the state object is null
 */
fun exactlyTwoChecked(state: State?): Boolean {
    return if(state == null) {
        false // Return false if state is not defined
    } else {
        val checkboxValues = extractCheckboxValues(state)
        exactlyTwoTrue(*checkboxValues)
    }
}

/**
 * extractCheckboxValues : extract the checkbox values from the state object
 * @param state : a state object
 * @return an array of checkbox values as booleans, or an empty array if an exception
 *          occurs or state object is null. It also logs the exception message
 */
fun extractCheckboxValues(state: State?): BooleanArray {
    return try {
        state?.checkbox?.checkboxList?.map { it.value }?.toBooleanArray() ?: booleanArrayOf()
    } catch (e: Exception) {
        Log.e("ExtractCheckboxValues", e.message, e)
        booleanArrayOf()
    }
}

/**
 * unitToFactor : Converts the given unit string to its corresponding
 *          factor value as specified by the SI prefixes.
 * @param unit : the string to extract the prefix from
 * @return The corresponding factor for the prefix of the unit string.
 *          If no prefix is found returns the unity factor (1.0)
 */
fun unitToFactor(unit: String): Double {
    val prefix = if (unit.length > 1) {
        "${unit[0]}"
    } else {
        "1"
    }
    return Units.fromPrefix(prefix).factor
}

/**
 * createUnit: create a string representation of a number with its unit
 * @param n : the value to be converted to string
 * @param suffix : the unit of the value
 * @return a string representation of the value with its unit
 */
fun createUnit(n: Double, suffix: String): String {
    val prefix = Units.fromFactor(n)
    return if (prefix.isEmpty()) suffix else "$prefix$suffix"
}

fun calculate(state: State) {
    var rFactor = unitToFactor(state.unit.R.value)
    var iFactor = unitToFactor(state.unit.I.value)
    var uFactor = unitToFactor(state.unit.U.value)
    var pFactor = unitToFactor(state.unit.P.value)
    val rTemp = state.value.R.value.toDoubleOrNull()?.times(rFactor)
    val iTemp = state.value.I.value.toDoubleOrNull()?.times(iFactor)
    val uTemp = state.value.U.value.toDoubleOrNull()?.times(uFactor)
    val pTemp = state.value.P.value.toDoubleOrNull()?.times(pFactor)

    // Calculate unknown variables using Ohm's law
    if (state.checkbox.R.value && state.checkbox.I.value) {
        if (rTemp != null && iTemp != null) {
            val u = rTemp * iTemp
            val p = u * iTemp
            val uUnit = createUnit(u, "V")
            state.unit.U.value = uUnit
            uFactor = unitToFactor(uUnit)
            state.value.U.value = (u / uFactor).toString()
            val pUnit = createUnit(p, "W")
            state.unit.P.value = pUnit
            pFactor = unitToFactor(pUnit)
            state.value.P.value = (p / pFactor).toString()
        }
    } else if (state.checkbox.R.value && state.checkbox.U.value) {
        if (rTemp != null && uTemp != null) {
            val i = uTemp / rTemp
            val p = uTemp * i
            val iUnit = createUnit(i, "A")
            state.unit.I.value = iUnit
            iFactor = unitToFactor(iUnit)
            state.value.I.value = (i / iFactor).toString()
            val pUnit = createUnit(p, "W")
            state.unit.P.value = pUnit
            pFactor = unitToFactor(pUnit)
            state.value.P.value = (p / pFactor).toString()
        }
    } else if (state.checkbox.R.value && state.checkbox.P.value) {
        if (rTemp != null && pTemp != null) {
            val i = sqrt(pTemp / rTemp)
            val u = rTemp * i
            val iUnit = createUnit(i, "A")
            state.unit.I.value = iUnit
            iFactor = unitToFactor(iUnit)
            state.value.I.value = (i / iFactor).toString()
            val uUnit = createUnit(u, "V")
            state.unit.U.value = uUnit
            uFactor = unitToFactor(uUnit)
            state.value.U.value = (u / uFactor).toString()
        }
    } else if (state.checkbox.I.value && state.checkbox.U.value) {
        if (iTemp != null && uTemp != null) {
            val p = iTemp * uTemp
            val r = uTemp / iTemp
            val pUnit = createUnit(p, "W")
            state.unit.P.value = pUnit
            pFactor = unitToFactor(pUnit)
            state.value.P.value = (p / pFactor).toString()
            val rUnit = createUnit(r, "Ω")
            state.unit.R.value = rUnit
            rFactor = unitToFactor(rUnit)
            state.value.R.value = (r / rFactor).toString()
        }
    } else if (state.checkbox.I.value && state.checkbox.P.value) {
        if (iTemp != null && pTemp != null) {
            val u = pTemp / iTemp
            val r = u / iTemp
            val uUnit = createUnit(u, "V")
            state.unit.U.value = uUnit
            uFactor = unitToFactor(uUnit)
            state.value.U.value = (u / uFactor).toString()
            val rUnit = createUnit(r, "Ω")
            state.unit.R.value = rUnit
            rFactor = unitToFactor(rUnit)
            state.value.R.value = (r / rFactor).toString()
        }
    } else if (state.checkbox.U.value && state.checkbox.P.value) {
        if (uTemp != null && pTemp != null) {
            val i = pTemp / uTemp
            val r = uTemp / i
            val iUnit = createUnit(i, "A")
            state.unit.I.value = iUnit
            iFactor = unitToFactor(iUnit)
            state.value.I.value = (i / iFactor).toString()
            val rUnit = createUnit(r, "Ω")
            state.unit.R.value = rUnit
            rFactor = unitToFactor(rUnit)
            state.value.R.value = (r / rFactor).toString()
        }
    }
}