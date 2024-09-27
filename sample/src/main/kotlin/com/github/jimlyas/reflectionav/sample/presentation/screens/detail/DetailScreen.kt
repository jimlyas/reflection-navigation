package com.github.jimlyas.reflectionav.sample.presentation.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.jimlyas.reflectionav.sample.data.Profile
import io.github.jimlyas.reflection.navigation.annotation.ReflectiveRoute

@ReflectiveRoute
data class DetailRoute(val item: List<Profile>)

@Composable
internal fun DetailScreen(profile: Profile) {
//    val vm = hiltViewModel<DetailViewModel>()

    Column(
        Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 5.dp)
    ) {
        Text(text = profile.name)
        Text(text = profile.age.toString())
        Text(text = profile.currentBalance.toPlainString())
    }
}