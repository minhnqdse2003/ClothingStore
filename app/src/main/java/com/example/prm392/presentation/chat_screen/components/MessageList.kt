import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier


@Composable
fun MessageList(messages: List<String>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(0.8f)
//            .weight(1f)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(messages.size) { index ->
            Text(
                color = androidx.compose.ui.graphics.Color.Black,
                text = messages[index],
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
        }
    }
}
