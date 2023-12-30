package mohsen.soltanian.controlflow.demo.core.data.annotations

import javax.inject.Qualifier

/** Annotation for Retrofit dependency.  */
@Qualifier
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FIELD
)
annotation class AuthorizationServicesScope()
