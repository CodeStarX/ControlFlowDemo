package mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.delegates

import mohsen.soltanian.controlflow.demo.core.data.sharedPrefrance.SharedPrefDelegate

/**
 * String prefs delegate
 */
class StringPrefDelegate(key: String, defaultValue: String? = "") : SharedPrefDelegate<String?>(key, defaultValue) {
    override fun getValue(): String? = prefs!!.getString(key, defaultValue)
    override fun setValue(value: String?) = prefs!!.edit().putString(key, value).apply()
}