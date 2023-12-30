package mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.delegates

import mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.SharedPrefDelegate

/**
 * Float prefs delegate
 */
class FloatPrefDelegate(key: String, defaultValue: Float = 0f) : SharedPrefDelegate<Float>(key, defaultValue) {
    override fun getValue() = prefs!!.getFloat(key, defaultValue)
    override fun setValue(value: Float) = prefs!!.edit().putFloat(key, value).apply()
}