package com.simdamdev.calculohm.utils


const val NANO_FACTOR = 0.000000001
const val MICRO_FACTOR = 0.000001
const val MILLI_FACTOR = 0.001
const val UNITY_FACTOR = 1.0
const val KILO_FACTOR = 1000.0
const val MEGA_FACTOR = 1000000.0
const val GIGA_FACTOR = 1000000000.0



/**
 * Units : class that stores the factors of various SI prefixes and provide helper functions to work with them.
 * The available prefixes are:
 * - NANO : prefix for 0.000000001
 * - MICRO : prefix for 0.000001
 * - MILLI : prefix for 0.001
 * - UNITY : prefix for 1.0 (default)
 * - KILO : prefix for 1000.0
 * - MEGA : prefix for 1000000.0
 * - GIGA : prefix for 1000000000.0
 * The companion object provides two methods:
 *  - fromPrefix(prefix: String): Double  that allows to find the corresponding
 *          factor given a string 'prefix'
 *  - fromFactor(number: Double): String that allows to find the corresponding
 *          prefix given a number
 */
class Units {
    companion object {
        // map that stores the factors of the prefixes
        private val prefixFactors = mapOf(
            "n" to NANO_FACTOR,
            "µ" to MICRO_FACTOR,
            "m" to MILLI_FACTOR,
            "" to UNITY_FACTOR,
            "k" to KILO_FACTOR,
            "M" to MEGA_FACTOR,
            "G" to GIGA_FACTOR
        )

        /**
         * Returns the factor of the prefix passed as parameter.
         * If the prefix is not found, it returns Double.NaN
         */
        fun fromPrefix(prefix: String): Double {
            return prefixFactors[prefix] ?: Double.NaN
        }

        /**
         * Returns the prefix that corresponds to the number passed as parameter.
         * The prefix is chosen by taking the prefix that has the closest factor less than or equal to the number passed as parameter.
         */
        fun fromFactor(number: Double): String {
            return if (number > 0){
                prefixFactors.filter { it.value <= number }.maxBy { it.value }.key
        } else ""
        }
    }
}
