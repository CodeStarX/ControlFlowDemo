package mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.delegates

import mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.SharedPrefDelegate

/**
 * Integer prefs delegate
 */
class IntPrefDelegate(key: String, defaultValue: Int = 0) : SharedPrefDelegate<Int>(key, defaultValue) {
    override fun getValue() = prefs!!.getInt(key, defaultValue)
    override fun setValue(value: Int) = prefs!!.edit().putInt(key, value).apply()
}