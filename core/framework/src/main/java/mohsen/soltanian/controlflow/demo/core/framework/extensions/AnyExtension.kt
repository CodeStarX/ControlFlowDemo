package mohsen.soltanian.controlflow.demo.core.framework.extensions

val Any.classTag: String get() = this.javaClass.canonicalName.orEmpty()
inline fun <reified T : Any> Any.cast(): T {
    return this as T
}