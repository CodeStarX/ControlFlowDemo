package mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.manager

import android.content.Context
import mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.delegates.BooleanPrefDelegate
import mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.delegates.FloatPrefDelegate
import mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.delegates.IntPrefDelegate
import mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.delegates.LongPrefDelegate
import mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.delegates.StringPrefDelegate
import mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.SharedPrefDelegate
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefCashManager @Inject constructor(
    @ApplicationContext context: Context
) {
    companion object {
        data class WriteParams(val key: String,val value: String)
        data class ReadParams(val key: String,val defaultValue: String)
    }
    init {
        SharedPrefDelegate.init(context = context)
    }

    fun <T> findPreference(key: String, default: T): T  {
        val res: Any = when (default) {
            is Long -> LongPrefDelegate(key = key, defaultValue = default).getValue()
            is String -> StringPrefDelegate(key = key, defaultValue = default).getValue()
            is Int -> IntPrefDelegate(key = key, defaultValue = default).getValue()
            is Boolean -> BooleanPrefDelegate(key = key, defaultValue = default).getValue()
            is Float -> FloatPrefDelegate(key = key, defaultValue = default).getValue()
            else -> throw IllegalArgumentException("This type cannot be saved into Preferences")
        }!!
        return res as T
    }

    fun <T> putPreference(key: String, value: T) {
        when (value) {
            is Long -> LongPrefDelegate(key).setValue(value)
            is String -> StringPrefDelegate(key).setValue(value)
            is Int -> IntPrefDelegate(key).setValue(value)
            is Boolean -> BooleanPrefDelegate(key).setValue(value)
            is Float -> FloatPrefDelegate(key).setValue(value)
            else -> throw IllegalArgumentException("This type cannot be saved into Preferences")
        }
    }
}