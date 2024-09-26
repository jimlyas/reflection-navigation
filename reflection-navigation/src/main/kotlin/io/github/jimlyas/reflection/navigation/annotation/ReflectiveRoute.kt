package io.github.jimlyas.reflection.navigation.annotation

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS

/**
 * [annotationClass] intended to be applied on the Route class for the navigation
 * apply it to the route class will prevent them for being obfuscated using R8 or dexguard
 * @author jimlyas
 */
@Target(CLASS)
@Retention(RUNTIME)
annotation class ReflectiveRoute