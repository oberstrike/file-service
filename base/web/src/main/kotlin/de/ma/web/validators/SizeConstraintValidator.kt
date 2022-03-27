package de.ma.web.validators

import de.ma.domain.nanoid.NanoId
import javax.enterprise.context.ApplicationScoped
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.constraints.Size

@ApplicationScoped
class SizeConstraintValidator : ConstraintValidator<Size, NanoId> {

    private var min: Int = 0
    private var max: Int = 0

    override fun initialize(constraintAnnotation: Size?) {
        min = constraintAnnotation?.min ?: 0
        max = constraintAnnotation?.max ?: 0
    }

    override fun isValid(p0: NanoId, p1: ConstraintValidatorContext): Boolean {
        return p0.id.length in min..max
    }

}