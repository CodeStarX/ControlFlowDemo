package mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance

import android.content.Context
import android.content.SharedPreferences

/** Delegate using [SharedPreferences]. [init] must be called before any use of these delegates.
 * @property key key in preferences
 * @property defaultValue default value
 * @see SharedPreferences
 */
abstract class SharedPrefDelegate<T>(val key: String, val defaultValue: T) {
    companion object {

        const val APPLICATION_PREF_NAME = "10@to@Million_application"
        var prefs: SharedPreferences? = null
            get() {
                if (field == null) throw IllegalStateException("You must call SharedPrefDelegate.init before using this delegate")
                return field
            }

        /**
         * Init the preferences with a context
         */
        fun init(context: Context) {
            prefs = context.getSharedPreferences(APPLICATION_PREF_NAME,Context.MODE_PRIVATE)
        }

    }

    abstract fun getValue(): T
    abstract fun setValue(value: T)
}