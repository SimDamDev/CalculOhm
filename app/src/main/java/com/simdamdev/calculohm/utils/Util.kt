package com.simdamdev.calculohm.utils

import android.util.Log
import kotlin.math.pow
import kotlin.math.sqrt



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
        ""
    }
    val temp = Units.fromPrefix(prefix)
    Log.d("UnitToFactor", "prefix: $prefix, factor: $temp")
    return Units.fromPrefix(prefix)
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
    val u = state.value.uTransformed.value
    val i = state.value.iTransformed.value
    val r = state.value.rTransformed.value
    val p = state.value.pTransformed.value
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
    val factor = Units.fromPrefix(prefix)
    val convertedValue = newValue / factor
    Log.d("updateValue", "$valueName: $newValue -> $convertedValue $prefix")
    when (valueName) {
        "R" -> {
            state.value.r.value = convertedValue.toString()
            state.unit.r.value = createUnit(newValue, suffix)
        }
        "I" -> {
            state.value.i.value = convertedValue.toString()
            state.unit.i.value = createUnit(newValue, suffix)
        }
        "U" -> {
            state.value.u.value = convertedValue.toString()
            state.unit.u.value = createUnit(newValue, suffix)
        }
        "P" -> {
            state.value.p.value = convertedValue.toString()
            state.unit.p.value = createUnit(newValue, suffix)
        }
    }
}


