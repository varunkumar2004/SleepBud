package com.varunkumar.myapplication.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ApplicationBottomBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BottomAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            text = "Sleep Sessions are detected automatically.\n" +
                    "You can change it here.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
    }
}