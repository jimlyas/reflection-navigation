package io.github.jimlyas.reflection.navigation.utilities

import io.github.jimlyas.reflection.navigation.utilities.Constants.EMPTY_STRING
import io.github.jimlyas.reflection.navigation.utilities.GsonUtilities.parseToJson
import java.math.BigDecimal

/**
 * Utilities class for casting types
 * @author jimlyas
 */
internal object CastingUtilities {

    /**
     * Function to parse [Any] into [String]
     * @return [String] parsed from [Any]
     * @receiver [Any]
     */
    fun Any.parseToString(): String = when (this) {
        String::class -> toString()
        Int::class -> toString()
        Boolean::class -> toString()
        Long::class -> toString()
        Float::class -> toString()
        Double::class -> toString()
        BigDecimal::class -> (this as BigDecimal).toPlainString()
        else -> try {
            parseToJson(this)
        } catch (t: Throwable) {
            EMPTY_STRING
        }
    }
}