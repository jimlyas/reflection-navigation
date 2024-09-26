package io.github.jimlyas.reflection.navigation.utilities

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.reflect.TypeToken
import io.github.jimlyas.reflection.navigation.utilities.CastingUtilities.parseToString
import io.github.jimlyas.reflection.navigation.utilities.GsonUtilities.parseToObject
import java.math.BigDecimal

/**
 *  pre-provided [NavType] to use in the reflection when registering route
 *  @author jimlyas
 */
internal object CustomNavType {

    /**
     * Function to get [NavType] for [BigDecimal]
     * @param isNullable Does the argument nullable
     * @return [NavType] for custom type [BigDecimal]
     */
    fun getBigDecimalNavType(isNullable: Boolean) = object : NavType<BigDecimal>(
        isNullableAllowed = isNullable
    ) {

        override fun get(bundle: Bundle, key: String): BigDecimal? {
            return parseValue(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): BigDecimal {
            return value.toBigDecimal()
        }

        override fun serializeAsValue(value: BigDecimal): String {
            return value.toPlainString()
        }

        override fun put(bundle: Bundle, key: String, value: BigDecimal) {
            bundle.putString(key, serializeAsValue(value))
        }
    }

    /**
     * Function to get  [NavType] for custom class
     * @param isNullable Does the argument nullable
     * @return [NavType] for the custom class
     */
    fun <args : Any> createNavType(
        typeToken: TypeToken<args>,
        isNullable: Boolean
    ) = object : NavType<args>(isNullableAllowed = isNullable) {

        override fun get(bundle: Bundle, key: String): args? {
            return parseValue(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): args = value.parseToObject(typeToken)

        override fun serializeAsValue(value: args) = parseToString()

        override fun put(bundle: Bundle, key: String, value: args) {
            bundle.putString(key, serializeAsValue(value))
        }
    }
}