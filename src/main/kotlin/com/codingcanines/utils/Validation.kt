package com.codingcanines.utils

import io.konform.validation.ValidationBuilder
import io.konform.validation.constraints.maxLength
import io.konform.validation.constraints.minLength
import io.konform.validation.constraints.pattern

fun ValidationBuilder<String>.validName(min: Int = 3, max: Int = 255) {
    minLength(min)
    maxLength(max)
    pattern(Regex("^[\\p{L} .'-]+$")) hint "contains invalid characters"
}
