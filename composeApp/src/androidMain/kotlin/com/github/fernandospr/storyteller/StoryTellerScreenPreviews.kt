package com.github.fernandospr.storyteller

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ErrorLoadingStoryScreenPreview() {
    ErrorLoadingStoryScreen("🐶", {})
}

@Preview
@Composable
fun LoadingStoryScreenPreview() {
    LoadingStoryScreen("🐶")
}

@Preview
@Composable
fun StoryScreenPreview() {
    StoryScreen("🐶", "Once upon a time...", {})
}

@Preview
@Composable
fun CharacterSelectionScreenPreview() {
    CharacterSelectionScreen(listOf(
        Character("dog", "🐶"),
        Character("cat", "🐱"),
        Character("lion", "🦁"),
        Character("monkey", "🐵"),
    )) {}
}

@Preview
@Composable
fun CharacterButtonPreview() {
    CharacterButton(uiDescription = "🐶") {}
}