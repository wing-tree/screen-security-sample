package wing.tree.screen.security.sample.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenSecurity

fun <T: Any> KClass<T>.isAnnotationPresent(annotationClass: KClass<out Annotation>): Boolean {
    return java.isAnnotationPresent(annotationClass.java)
}

fun <T: Any> KClass<T>.isNotAnnotationPresent(annotationClass: KClass<out Annotation>): Boolean {
    return isAnnotationPresent(annotationClass).not()
}
