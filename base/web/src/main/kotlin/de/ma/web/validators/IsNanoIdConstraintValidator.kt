package de.ma.web.validators

import de.ma.domain.nanoid.NanoId
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass


@MustBeDocumented
@Constraint(validatedBy = [IsNanoIdConstraintValidator::class])
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.VALUE_PARAMETER

)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable()
annotation class IsNanoId(
    val message: String = "You are using a Reserved Word",
    val payload: Array<KClass<out Payload>> = [],
    val groups: Array<KClass<*>> = []
)


class IsNanoIdConstraintValidator : ConstraintValidator<IsNanoId, NanoId> {
    override fun initialize(constraintAnnotation: IsNanoId) {}

    override fun isValid(value: NanoId, context: ConstraintValidatorContext): Boolean {
        return value.id.matches(Regex("^[0-9a-zA-Z_-]{21}$"))
    }

}