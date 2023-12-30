package mohsen.soltanian.controlflow.demo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import mohsen.soltanian.controlflow.demo.core.data.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class ControlFlowDemo: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}