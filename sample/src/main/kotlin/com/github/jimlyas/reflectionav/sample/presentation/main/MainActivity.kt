package com.github.jimlyas.reflectionav.sample.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.github.jimlyas.reflectionav.sample.presentation.screens.detail.DetailRoute
import com.github.jimlyas.reflectionav.sample.presentation.screens.detail.DetailScreen
import com.github.jimlyas.reflectionav.sample.presentation.screens.list.ListRoute
import com.github.jimlyas.reflectionav.sample.presentation.screens.list.ListScreen
import com.github.jimlyas.reflectionav.sample.presentation.theme.ReflectionNavigationTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.jimlyas.reflection.navigation.destination.getArg
import io.github.jimlyas.reflection.navigation.navigation.navigateTo
import io.github.jimlyas.reflection.navigation.route.composeRoute
import io.github.jimlyas.reflection.navigation.utilities.ReflectionUtilities.asRouteName

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
                        composeRoute<ListRoute> {
                            ListScreen { controller.navigateTo(DetailRoute(listOf(it))) }
                        }

                        composeRoute<DetailRoute> {
                            val args = remember { it.getArg<DetailRoute>() }
                            args?.let { profile -> DetailScreen(profile.item.first()) }
                        }
                    }
                }
            }
        }
    }
}