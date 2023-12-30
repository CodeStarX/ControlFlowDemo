package mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.delegates

import mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.SharedPrefDelegate

/**
 * Long prefs delegate
 */
class LongPrefDelegate(key: String, defaultValue: Long = 0L) : SharedPrefDelegate<Long>(key, defaultValue) {
    override fun getValue() = prefs!!.getLong(key, defaultValue)
    override fun setValue(value: Long) = prefs!!.edit().putLong(key, value).apply()
}