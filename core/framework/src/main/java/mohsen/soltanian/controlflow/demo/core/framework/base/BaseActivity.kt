package mohsen.soltanian.controlflow.demo.core.framework.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.Keep

@Keep
abstract class BaseActivity: ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUi(savedInstanceState = savedInstanceState)
    }

    protected abstract fun setupUi(savedInstanceState: Bundle?)
}