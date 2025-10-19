package com.varunkumar.myapplication.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ButtonView(
    modifier: Modifier = Modifier.Companion,
    cornerShape: Dp = 200.dp,
    padding: Dp = 30.dp,
    backgroundColor: Color,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerShape))
            .background(backgroundColor)
            .padding(vertical = padding)
            .clickable { onClick() },
        contentAlignment = Alignment.Companion.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Companion.Center,
            style = MaterialTheme.typography.displaySmall
        )
    }
}