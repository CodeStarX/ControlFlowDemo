package mohsen.soltanian.controlflow.demo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mohsen.soltanian.controlflow.demo.ui.theme.ControlFlowDemoTheme

@Composable
fun ErrorView(errMessage: String,tryAgain: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Error Message is:",textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 4.dp))
        Text(text = errMessage,textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp))
        Button(onClick = { tryAgain() }) {
            Text(text = "Try Again")
        }

    }
}


@Preview(showSystemUi = true)
@Composable
fun ErrorViewPreview() {
    ControlFlowDemoTheme {
        ErrorView(errMessage = "Error Message"){ }
    }

}