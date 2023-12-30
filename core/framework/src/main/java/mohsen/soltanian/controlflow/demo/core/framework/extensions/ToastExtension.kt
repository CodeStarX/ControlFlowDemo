package mohsen.soltanian.controlflow.demo.core.framework.extensions

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun Activity.exitToast(message: String): Toast {
    return Toast.makeText(this, message,Toast.LENGTH_LONG )
}
fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}