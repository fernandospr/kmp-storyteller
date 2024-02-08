package com.github.fernandospr.storyteller.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun StoryScreenPreview() {
    StoryScreen("🐶", "Once upon a time...", isPlaying = false, { _ -> }, {}, {})
}