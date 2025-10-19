package com.varunkumar.myapplication.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.varunkumar.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationTopBar(
    modifier: Modifier = Modifier
) {
    LargeTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(R.string.app_name)) },
        actions = {
            Row() {

            }
        }
    )
}