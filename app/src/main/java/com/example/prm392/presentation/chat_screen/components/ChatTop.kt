import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontFamily = FontFamily.Serif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    )
}