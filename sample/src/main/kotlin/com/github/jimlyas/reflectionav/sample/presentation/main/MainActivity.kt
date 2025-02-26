package com.github.jimlyas.reflectionav.sample.presentation.main

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.github.jimlyas.reflectionav.sample.presentation.screens.detail.DetailRoute
import com.github.jimlyas.reflectionav.sample.presentation.screens.detail.DetailScreen
import com.github.jimlyas.reflectionav.sample.presentation.screens.list.ListRoute
import com.github.jimlyas.reflectionav.sample.presentation.screens.list.ListScreen
import com.github.jimlyas.reflectionav.sample.presentation.theme.ReflectionNavigationTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jimlyas.reflection.navigation.destination.getArg
import io.github.jimlyas.reflection.navigation.destination.isFromDeeplink
import io.github.jimlyas.reflection.navigation.navigation.navigateTo
import io.github.jimlyas.reflection.navigation.route.composeRoute
import io.github.jimlyas.reflection.navigation.utilities.ReflectionUtilities.asRouteName
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReflectionNavigationTheme {
                Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                    val controller = rememberNavController()

                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = controller,
                        startDestination = ListRoute::class.asRouteName()
                    ) {
                        val sample = "https://io.github.jimlyas/detail."

                        composeRoute<ListRoute> {
                            ListScreen { item, index ->
                                if (index % 2 == 0) controller.navigateTo(DetailRoute(item = item))
                                else controller.navigate(Uri.parse(sample))
                            }
                        }

                        composeRoute<DetailRoute>(
                            deepLinks = listOf(navDeepLink { uriPattern = sample })
                        ) {
                            val args = remember { it.getArg<DetailRoute>() }
                            Timber.d("fromDeeplink: ${it.isFromDeeplink()}")

                            args?.let { argument ->
                                argument.item?.let { profile ->
                                    DetailScreen(profile, controller::popBackStack)
                                } ?: Text(text = "profile is empty")
                            } ?: Text(text = "args is empty")
                        }
                    }
                }
            }
        }
    }
}