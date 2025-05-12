package com.gitusers.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.gitusers.R
import com.gitusers.ui.theme.BlueGray300


@Composable
fun CircularUserImageWithPlaceholderView(
    imageUrl: String,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    @DrawableRes fallbackResId: Int = R.drawable.ic_launcher_foreground
) {
    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        placeholder = painterResource(fallbackResId),
        error = painterResource(fallbackResId)
    )

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color = BlueGray300)
    )
}

@Composable
@Preview
fun CircularUserImageWithPlaceholderViewPreview() {
    Box(modifier = Modifier.background(color = Color.White)) {
        CircularUserImageWithPlaceholderView("")
    }
}
