package com.github.fernandospr.storyteller.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun BackHandler(onBack: () -> Unit) {
  BackHandler(onBack = onBack)
}