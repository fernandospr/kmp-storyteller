package com.github.fernandospr.storyteller.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.github.fernandospr.storyteller.MR
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ErrorLoadingStoryScreen(
    uiDescription: String,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    BackHandler { onBackClick() }

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
                fontFamily = fontFamilyResource(MR.fonts.Alegreya.regular),
                style = LocalTextStyle.current.merge(
                    TextStyle(lineHeight = 1.5.em)
                ),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
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
