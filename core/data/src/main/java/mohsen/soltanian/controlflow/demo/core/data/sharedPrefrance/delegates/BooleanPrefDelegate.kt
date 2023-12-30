package mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.delegates

import mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.SharedPrefDelegate

/**
 * Boolean prefs delegate
 */
class BooleanPrefDelegate(key: String, defaultValue: Boolean = false) : SharedPrefDelegate<Boolean>(key, defaultValue) {
    override fun getValue() = prefs!!.getBoolean(key, defaultValue)
    override fun setValue(value: Boolean) = prefs!!.edit().putBoolean(key, value).apply()
}