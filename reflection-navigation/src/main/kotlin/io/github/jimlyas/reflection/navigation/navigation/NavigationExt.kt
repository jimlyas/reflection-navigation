package io.github.jimlyas.reflection.navigation.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.Navigator.Extras
import io.github.jimlyas.reflection.navigation.utilities.CastingUtilities.parseToString
import io.github.jimlyas.reflection.navigation.utilities.Constants.NAVIGATION_AUTHORITY
import io.github.jimlyas.reflection.navigation.utilities.Constants.NAVIGATION_SCHEME
import io.github.jimlyas.reflection.navigation.utilities.ReflectionUtilities.asRouteName
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * Function to trigger navigation to [destination] route
 *
 * @author jimlyas
 * @param route [destination] class that will be declared as the route
 * @param navOptions special options for this navigation operation
 * @param navExtras extras to pass to the Navigator
 * @receiver [NavController]
 */
fun <destination : Any> NavController.navigateTo(
    route: destination,
    navOptions: NavOptions? = null,
    navExtras: Extras? = null
) {
    val kClass = route::class

    val intendedUri = Uri.Builder()
        .scheme(NAVIGATION_SCHEME)
        .authority(NAVIGATION_AUTHORITY)
        .path(kClass.asRouteName())

    kClass
        .declaredMemberProperties
        .forEach { property ->
            property.isAccessible = true
            val name = property.name
            val value = property.getter.call(route)

            value?.let {
                intendedUri.appendQueryParameter(
                    name, value.parseToString()
                )
            }
        }

    navigate(
        request = NavDeepLinkRequest.Builder.fromUri(intendedUri.build()).build(),
        navOptions = navOptions,
        navigatorExtras = navExtras
    )
}

