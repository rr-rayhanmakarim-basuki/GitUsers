package com.gitusers.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gitusers.R
import com.gitusers.ui.theme.PurpleGrey40

@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    message: String = stringResource(R.string.default_loading_message)
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = PurpleGrey40,
                strokeWidth = 4.dp,
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .size(64.dp)
            )
            Text(
                text = message,
                color = PurpleGrey40,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
fun LoadingViewPreview() {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        LoadingView()
    }
}