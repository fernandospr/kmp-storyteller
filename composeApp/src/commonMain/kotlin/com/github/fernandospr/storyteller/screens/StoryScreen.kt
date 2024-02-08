package com.github.fernandospr.storyteller.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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