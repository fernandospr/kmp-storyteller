package com.github.fernandospr.storyteller

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoadingStoryScreen(uiDescription: String) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    Column(
        Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(text = uiDescription, fontSize = 100.sp, modifier = Modifier.rotate(angle))
    }
}

@Composable
fun StoryScreen(uiDescription: String, story: String, onResetClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = uiDescription, fontSize = 60.sp, modifier = Modifier.padding(30.dp))
        Text(
            text = story,
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .weight(1f)
                .verticalScroll(rememberScrollState())
        )
        OutlinedButton(
            shape = CircleShape, modifier = Modifier.padding(8.dp), onClick = onResetClick
        ) {
            Text(text = "🔄", fontSize = 60.sp, modifier = Modifier.padding(30.dp))
        }
    }
}

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