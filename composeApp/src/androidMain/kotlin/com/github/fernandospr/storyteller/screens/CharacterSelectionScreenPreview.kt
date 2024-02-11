package com.github.fernandospr.storyteller.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.fernandospr.storyteller.data.Character

@Preview
@Composable
fun CharacterSelectionScreenPreview() {
    CharacterSelectionScreen(
        listOf(
            Character("dog", "🐶"),
            Character("cat", "🐱"),
            Character("lion", "🦁"),
            Character("monkey", "🐵"),
        )
    ) {}
}