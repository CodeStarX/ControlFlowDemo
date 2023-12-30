package mohsen.soltanian.controlflow.demo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import mohsen.soltanian.controlflow.demo.ui.theme.ControlFlowDemoTheme

@Composable
fun ProgressDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Box(
            contentAlignment= Center,
            modifier = Modifier
                .size(120.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Please Wait...", color = Color.Gray, fontSize = 13.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressDialogPreview() {
    ControlFlowDemoTheme {
        ProgressDialog { }
    }
}