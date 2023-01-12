package com.simdamdev.calculohm.utils

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import kotlin.math.pow
import kotlin.math.sqrt


class ColorList(
    val col1: Color,
    val col2: Color,
    val col3: Color,
    val col4: Color,
)

class State(
    val checkbox: CheckboxState = CheckboxState(),
    val unit: UnitState = UnitState(),
    val value: ValueState = ValueState(unit= unit),

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

fun calculateR(u: Double?, i: Double?, p:Double?): Double {
    return when {
        i != null && u != null && u != 0.0 && i != 0.0 -> u / i
        p != null && u != null && u != 0.0 && p != 0.0 -> u.pow(2) / p
        p != null && i != null && p != 0.0 && i != 0.0 -> p / i.pow(2)
        else -> 0.0
    }
}

fun calculateI(u: Double?, r: Double?, p:Double?): Double {
    Log.d("calculateI", "u=$u, r=$r, p=$p")
    return when {
        r != null && u != null && u != 0.0 && r != 0.0 -> u / r
        p != null && u != null && u != 0.0 && p != 0.0 -> p / u
        p != null && r != null && p != 0.0 && r != 0.0 -> sqrt(p / r)
        else -> 0.0
    }
}

fun calculateU(r: Double?, i: Double?, p:Double?): Double {
    return when {
        i != null && r != null && i != 0.0 && r != 0.0 -> r * i
        p != null && r != null && p != 0.0 && r != 0.0 -> sqrt(p * r)
        p != null && i != null && p != 0.0 && i != 0.0 -> p / i
        else -> 0.0
    }
}

fun calculateP(u: Double?, i: Double?, r:Double?): Double {
    return when {
        u != null && i != null && u != 0.0 && i != 0.0 -> u * i
        u != null && r != null && u != 0.0 && r != 0.0 -> u.pow(2) / r
        i != null && r != null && i != 0.0 && r != 0.0 -> i.pow(2) * r
        else -> 0.0
    }
}


fun findMissing(state: State): List<String> {
    val checkboxValues = extractCheckboxValues(state)
    val list = listOf("R", "I", "U", "P").filterIndexed { index, _ -> !checkboxValues[index] }
    Log.d("findMissing", list.toString())
    return list
}

fun updateValues(state: State) {
    val u = state.value.U_transformed.value
    val i = state.value.I_transformed.value
    val r = state.value.R_transformed.value
    val p = state.value.P_transformed.value
    val missing = findMissing(state)
    if (missing.contains("R")) {
        val newR = calculateR(u, i, p)
        updateValue(state, "R", newR)
    }
    if (missing.contains("I")) {
        val newI = calculateI(u, r, p)
        updateValue(state, "I", newI)
    }
    if (missing.contains("U")) {
        val newU = calculateU(i, r, p)
        updateValue(state, "U", newU)
    }
    if (missing.contains("P")) {
        val newP = calculateP(u, i, r)
        updateValue(state, "P", newP)
    }
}

fun updateValue(state: State, valueName: String, newValue: Double) {
    val suffix =
        when (valueName) {
            "R" -> "Ω"
            "I" -> "A"
            "U" -> "V"
            "P" -> "W"
            else -> ""
        }
    val prefix = Units.fromFactor(newValue)
    val factor = Units.fromPrefix(prefix).factor
    val convertedValue = newValue / factor
    Log.d("updateValue", "$valueName: $newValue -> $convertedValue $prefix")
    when (valueName) {
        "R" -> {
            state.value.R.value = convertedValue.toString()
            state.unit.R.value = createUnit(newValue, suffix)
        }
        "I" -> {
            state.value.I.value = convertedValue.toString()
            state.unit.I.value = createUnit(newValue, suffix)
        }
        "U" -> {
            state.value.U.value = convertedValue.toString()
            state.unit.U.value = createUnit(newValue, suffix)
        }
        "P" -> {
            state.value.P.value = convertedValue.toString()
            state.unit.P.value = createUnit(newValue, suffix)
        }
    }
}


