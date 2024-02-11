package com.github.fernandospr.storyteller.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.fernandospr.storyteller.data.Character

@Composable
fun CharacterSelectionScreen(
    characters: List<Character>,
    onNewStoryClick: (character: Character) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxSize(),
        content = {
            items(characters) {
                CharacterButton(it.uiDescription) {
                    onNewStoryClick(it)
                }
            }
        }
    )
}

@Composable
fun CharacterButton(
    uiDescription: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        shape = CircleShape,
        modifier = Modifier.padding(8.dp),
        onClick = { onClick() }) {
        Text(text = uiDescription, fontSize = 60.sp, modifier = Modifier.padding(30.dp))
    }
}