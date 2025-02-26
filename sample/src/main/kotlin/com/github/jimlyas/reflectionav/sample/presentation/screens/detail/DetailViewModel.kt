package com.github.jimlyas.reflectionav.sample.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jimlyas.reflection.navigation.destination.getArg
import io.github.jimlyas.reflection.navigation.destination.isFromDeeplink
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    val args = savedStateHandle.getArg<DetailRoute>()
    val isFromDeeplink = savedStateHandle.isFromDeeplink()
}