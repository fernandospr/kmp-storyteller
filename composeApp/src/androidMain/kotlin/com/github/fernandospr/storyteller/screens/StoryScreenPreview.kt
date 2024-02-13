package com.github.fernandospr.storyteller.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun StoryScreenPreview() {
    StoryScreen("🐶", Color.White, "Once upon a time...", isPlaying = false, { _ -> }, {}, {})
}