package com.github.fernandospr.storyteller

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ErrorLoadingStoryScreen(
    uiDescription: String,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(120.dp),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = uiDescription,
                            fontSize = 60.sp,
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.padding(start = 24.dp).width(48.dp))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, "back")
                    }
                },
                backgroundColor = Color.White
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(MR.strings.error),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(16.dp)
            )
            OutlinedButton(
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.primary),
                shape = CircleShape,
                modifier = Modifier.padding(8.dp).size(80.dp),
                onClick = { onRetryClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "retry",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun LoadingStoryScreen(uiDescription: String) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "scale"
    )
    Box(modifier = Modifier.fillMaxSize())
    {
        Text(text = uiDescription,
            fontSize = 60.sp,
            modifier = Modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    transformOrigin = TransformOrigin.Center
                }
                .align(Alignment.Center),
            style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated)
        )
    }
}

@Composable
fun StoryScreen(
    uiDescription: String,
    story: String,
    isPlaying: Boolean,
    onPlayClick: (story: String) -> Unit,
    onStopClick: () -> Unit,
    onResetClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(120.dp),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = uiDescription,
                            fontSize = 60.sp,
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.padding(start = 24.dp).width(48.dp))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onResetClick) {
                        Icon(Icons.Filled.ArrowBack, "back")
                    }
                },
                backgroundColor = Color.White
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.primary,
                onClick = {
                    if (isPlaying) {
                        onStopClick()
                    } else {
                        onPlayClick(story)
                    }
                }
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "stop" else "play",
                    tint = Color.White
                )
            }
        }
    ) {
        Text(
            text = story,
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        )
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