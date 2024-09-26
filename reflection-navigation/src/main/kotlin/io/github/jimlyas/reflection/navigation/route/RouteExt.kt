package io.github.jimlyas.reflection.navigation.route

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.jimlyas.reflection.navigation.utilities.Constants.QUESTION_MARK
import io.github.jimlyas.reflection.navigation.utilities.ReflectionUtilities.toNavType
import kotlin.reflect.full.primaryConstructor

/**
 * Function to declare a route definition for you navigation graph
 *
 * @author jimlyas
 * @param deepLinks list of deep links to associate with the destinations
 * @param enterTransition callback to determine the destination's enter transition
 * @param exitTransition callback to determine the destination's exit transition
 * @param popEnterTransition callback to determine the destination's popEnter transition
 * @param popExitTransition callback to determine the destination's popExit transition
 * @param sizeTransform callback to determine the destination's sizeTransform.
 * @param content composable for the destination
 * @receiver [NavGraphBuilder]
 */
inline fun <reified routeClass : Any> NavGraphBuilder.composeRoute(
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline enterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    EnterTransition?)? = null,
    noinline exitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    ExitTransition?)? = null,
    noinline popEnterTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    EnterTransition?)? = enterTransition,
    noinline popExitTransition:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    ExitTransition?)? = exitTransition,
    noinline sizeTransform:
    (AnimatedContentTransitionScope<NavBackStackEntry>.() -> @JvmSuppressWildcards
    SizeTransform?)? = null,
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    val routeKClass = routeClass::class

    val args = routeKClass.primaryConstructor?.parameters?.map { param ->
        navArgument(param.name.orEmpty()) {
            type = param.toNavType()
            nullable = param.type.isMarkedNullable
        }
    }.orEmpty()

    val routeName = buildString {
        append("${routeKClass.qualifiedName}")
        append(QUESTION_MARK)
        args.map { it.name }.forEach { s -> append("$s={$s}&") }
        deleteAt(lastIndex)
    }

    composable(
        route = routeName,
        arguments = args,
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        sizeTransform = sizeTransform,
        content = content
    )
}