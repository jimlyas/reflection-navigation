package com.github.jimlyas.reflectionav.sample.presentation.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.jimlyas.reflectionav.sample.data.Profile
import com.github.jimlyas.reflectionav.sample.data.dummyData
import io.github.jimlyas.reflection.navigation.annotation.ReflectiveRoute

@ReflectiveRoute
object ListRoute

@Composable
internal fun ListScreen(onNavigate: (Profile) -> Unit) {
    LazyColumn {
        items(
            items = dummyData,
            key = { it.id }
        ) { item ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(item) }
            ) {
                Column(
                    Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 5.dp)
                ) {
                    Text(text = item.name)
                    Text(text = item.age.toString())
                }
                HorizontalDivider()
            }
        }
    }
}