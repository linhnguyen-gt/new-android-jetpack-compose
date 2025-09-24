package com.newAndroid.newandroidjetpackcompose.presentation.ui.forms

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
class FormState<T>(
    private val initialValue: T,
    private val validation: ValidationRule? = null
) {
    private var _value by mutableStateOf(initialValue)
    val value: T get() = _value

    private var _error by mutableStateOf<String?>(null)
    val error: String? get() = _error

    private var _isDirty by mutableStateOf(false)
    val isDirty: Boolean get() = _isDirty

    val isValid: Boolean get() = _error == null

    init {}

    fun updateValue(newValue: T) {
        _value = newValue
        _isDirty = true
        _error = validation?.validate(_value) ?: null
    }

    fun validate(): Boolean {
        _error = validation?.validate(_value) ?: null
        return _error == null
    }

    fun forceValidate(): Boolean {
        _isDirty = true
        _error = validation?.validate(_value) ?: null
        return _error == null
    }

    fun clearError() {
        _error = null
    }

    fun reset() {
        _value = initialValue
        _error = null
        _isDirty = false
    }
}

@Stable
class ValidationRule private constructor(
    private val validators: List<(Any?) -> String?>
) {
    fun validate(value: Any?): String? {
        for (validator in validators) {
            val error = validator(value)
            if (error != null) return error
        }
        return null
    }

    companion object {
        fun create(vararg validators: (Any?) -> String?): ValidationRule {
            return ValidationRule(validators.toList())
        }

        fun required(message: String = "This field is required"): (Any?) -> String? = { value ->
            when (value) {
                is String -> if (value.isBlank()) message else null
                is Number -> if (value.toDouble() == 0.0) message else null
                null -> message
                else -> null
            }
        }

        fun email(message: String = "Invalid email format"): (Any?) -> String? = { value ->
            when (value) {
                is String -> {
                    if (value.isBlank()) null  // Don't show error for blank, let required handle it
                    else if (android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) null
                    else message
                }

                else -> null
            }
        }

        fun minLength(min: Int, message: String = "Minimum length is $min"): (Any?) -> String? =
            { value ->
                when (value) {
                    is String -> {
                        if (value.length >= min) null
                        else message
                    }

                    else -> null
                }
            }

        fun maxLength(max: Int, message: String = "Maximum length is $max"): (Any?) -> String? =
            { value ->
                when (value) {
                    is String -> {
                        if (value.length <= max) null
                        else message
                    }

                    else -> null
                }
            }

        fun custom(validator: (Any?) -> String?): (Any?) -> String? = validator

        fun phone(message: String = "Invalid phone number"): (Any?) -> String? = { value ->
            when (value) {
                is String -> {
                    if (value.isBlank()) null
                    else if (value.matches(Regex("^[+]?[0-9]{10,15}$"))) null
                    else message
                }

                else -> null
            }
        }

        fun password(
            minLength: Int = 8,
            message: String = "Password must be at least $minLength characters"
        ): (Any?) -> String? = { value ->
            when (value) {
                is String -> {
                    if (value.isBlank()) null
                    else if (value.length < minLength) message
                    else null
                }

                else -> null
            }
        }

        fun confirmPassword(
            originalPassword: () -> String,
            message: String = "Passwords do not match"
        ): (Any?) -> String? = { value ->
            when (value) {
                is String -> {
                    if (value.isBlank()) null
                    else if (value == originalPassword()) null
                    else message
                }

                else -> null
            }
        }

        fun numeric(message: String = "Must be a number"): (Any?) -> String? = { value ->
            when (value) {
                is String -> {
                    if (value.isBlank()) null
                    else if (value.matches(Regex("^[0-9]+$"))) null
                    else message
                }

                else -> null
            }
        }

        fun alphanumeric(message: String = "Must contain only letters and numbers"): (Any?) -> String? =
            { value ->
                when (value) {
                    is String -> {
                        if (value.isBlank()) null
                        else if (value.matches(Regex("^[a-zA-Z0-9]+$"))) null
                        else message
                    }

                    else -> null
                }
            }
    }
}
