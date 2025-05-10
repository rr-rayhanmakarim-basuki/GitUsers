import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gitusers.R
import com.gitusers.ui.theme.PurpleGrey40

@Composable
fun EmptyResultView(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(R.drawable.il_default_no_result),
    message: String = stringResource(R.string.default_empty_result_message),
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 120.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .size(120.dp)
            )
            Text(
                text = message,
                color = PurpleGrey40,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun EmptyResultPreview() {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        EmptyResultView()
    }
}

