package com.simdamdev.calculohm.utils


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
