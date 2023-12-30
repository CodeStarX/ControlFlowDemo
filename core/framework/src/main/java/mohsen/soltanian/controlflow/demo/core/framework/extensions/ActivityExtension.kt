package mohsen.soltanian.controlflow.demo.core.framework.extensions

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback

fun ComponentActivity.handleBackPress(block: () -> Unit) {
    val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                block()
            }
        }
    onBackPressedDispatcher.addCallback(this, callback)
}