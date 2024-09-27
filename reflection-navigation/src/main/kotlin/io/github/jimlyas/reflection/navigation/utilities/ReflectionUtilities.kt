package io.github.jimlyas.reflection.navigation.utilities

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import io.github.jimlyas.reflection.navigation.utilities.Constants.DOT
import io.github.jimlyas.reflection.navigation.utilities.Constants.THREE
import io.github.jimlyas.reflection.navigation.utilities.GsonUtilities.getTypeToken
import io.github.jimlyas.reflection.navigation.utilities.GsonUtilities.parseToObject
import java.math.BigDecimal
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.createType

/**
 * Utilities for [kotlin.reflect] classes
 * @author jimlyas
 */
object ReflectionUtilities {
    /**
     * Function to check does given [KParameter] is a data class or not
     *
     * @return [Boolean] Does the [KParameter] is a data class or not?
     * @receiver [KParameter]
     */
    private fun KParameter.isDataClass(): Boolean =
        (this.type.classifier as? KClass<*>)?.isData ?: false

    /**
     * Function to return [NavType] from given [KParameter]
     * @return [NavType] based on [KParameter.type]
     * @receiver [KParameter]
     */
    fun KParameter.toNavType() = when (this.type.classifier) {
        Int::class -> NavType.IntType
        Boolean::class -> NavType.BoolType
        Float::class -> NavType.FloatType
        Long::class -> NavType.LongType
        String::class -> NavType.StringType
        BigDecimal::class -> NavType.StringType
        List::class -> when (this.type.arguments.firstOrNull()?.type?.classifier) {
            Int::class -> NavType.IntListType
            Boolean::class -> NavType.BoolListType
            Float::class -> NavType.FloatListType
            Long::class -> NavType.LongListType
            String::class -> NavType.StringListType
            else -> {
                if (this.isDataClass()) NavType.StringType
                else NavType.StringListType
            }
        }

        else -> if (this.isDataClass()) NavType.StringType else NavType.StringType
    }

    /**
     * Function to load value from [savedStateHandle] based on key name
     * derived from [KParameter.name]
     *
     * @param savedStateHandle instance of [SavedStateHandle] where all the required data is stored
     * @return [Any] value from [savedStateHandle]
     * @receiver [KParameter]
     */
    fun KParameter.getValueFrom(savedStateHandle: SavedStateHandle): Any? = when (this.type) {
        String::class.createType() -> savedStateHandle.get<String>(this.name.orEmpty())
        Int::class.createType() -> savedStateHandle.get<Int>(this.name.orEmpty())
        Boolean::class.createType() -> savedStateHandle.get<Boolean>(this.name.orEmpty())
        Long::class.createType() -> savedStateHandle.get<Long>(this.name.orEmpty())
        Float::class.createType() -> savedStateHandle.get<Float>(this.name.orEmpty())
        Double::class.createType() -> savedStateHandle.get<Double>(this.name.orEmpty())
        BigDecimal::class.createType() -> savedStateHandle.get<String>(this.name.orEmpty())
            ?.toBigDecimal()

        else -> try {
            if ((this.type.classifier as? KClass<*>)?.java == List::class.java) {
                val argType = List::class.createType(this.type.arguments)

                savedStateHandle.get<Array<String>>(this.name.orEmpty())
                    ?.map { it.parseToObject(getTypeToken(argType)) }
                    ?.first()

            } else savedStateHandle.get<String>(this.name.orEmpty())
                ?.parseToObject(getTypeToken(this.type))
        } catch (t: Throwable) {
            null
        }
    }

    /**
     * Function to load value from [bundle] based on key name
     * derived from [KParameter.name]
     *
     * @param bundle instance of [Bundle] where all the required data is stored
     * @return [Any] value from [Bundle]
     * @receiver [KParameter]
     */
    fun KParameter.getValueFrom(bundle: Bundle): Any? = when (this.type) {
        String::class.createType() -> bundle.getString(this.name.orEmpty())
        Int::class.createType() -> bundle.getInt(this.name.orEmpty())
        Boolean::class.createType() -> bundle.getBoolean(this.name.orEmpty())
        Long::class.createType() -> bundle.getLong(this.name.orEmpty())
        Float::class.createType() -> bundle.getFloat(this.name.orEmpty())
        Double::class.createType() -> bundle.getDouble(this.name.orEmpty())
        BigDecimal::class.createType() -> bundle.getString(this.name.orEmpty())
            ?.toBigDecimal()

        else -> try {
            if ((this.type.classifier as? KClass<*>)?.java == List::class.java) {
                val argType = List::class.createType(this.type.arguments)

                bundle.getStringArray(this.name.orEmpty())
                    ?.map { it.parseToObject(getTypeToken(argType)) }
                    ?.first()

            } else bundle.getString(this.name.orEmpty())
                ?.parseToObject(getTypeToken(this.type))
        } catch (t: Throwable) {
            null
        }
    }

    /**
     * Function to return [KClass] with its' package name as the route
     * Will return at most three words and two dots in-between
     * @return [String] for the URL
     * @receiver [KClass]
     */
    fun KClass<*>.asRouteName(): String {
        val input = qualifiedName.orEmpty()
        val parts = input.split(DOT)

        return if (parts.size <= THREE) input
        else parts.takeLast(THREE).joinToString(DOT)
    }
}