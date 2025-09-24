package com.newAndroid.newandroidjetpackcompose.presentation.ui.forms

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
class FormBuilder {
    private val _fields = mutableMapOf<String, FormState<String>>()
    val fields: Map<String, FormState<String>> get() = _fields

    fun addField(
        name: String,
        initialValue: String = "",
        validation: ValidationRule? = null
    ): FormBuilder {
        _fields[name] = FormState(initialValue, validation)
        return this
    }

    fun getField(name: String): FormState<String>? = _fields[name]

    fun validateAll(): Boolean {
        return _fields.values.all { it.validate() }
    }

    fun forceValidateAll(): Boolean {
        _fields.values.forEach { it.forceValidate() }
        return _fields.values.all { it.isValid }
    }

    fun isValid(): Boolean {
        return _fields.values.all { it.isValid }
    }

    fun resetAll() {
        _fields.values.forEach { it.reset() }
    }

    fun getValues(): Map<String, String> {
        return _fields.mapValues { it.value.value }
    }

    fun hasErrors(): Boolean {
        return _fields.values.any { it.error != null }
    }

    fun getErrors(): Map<String, String> {
        return _fields.filter { it.value.error != null }
            .mapValues { it.value.error!! }
    }
}

@Stable
enum class FormFieldType {
    EMAIL,
    PASSWORD,
    TEXT,
    NUMBER,
    PHONE
}

@Stable
object FormFieldFactory {
    fun createField(
        type: FormFieldType,
        initialValue: String = "",
        customValidation: ValidationRule? = null
    ): FormState<String> {
        val validation = customValidation ?: when (type) {
            FormFieldType.EMAIL -> ValidationRule.create(ValidationRule.email("Invalid email format"))
            FormFieldType.PASSWORD -> ValidationRule.create(ValidationRule.required())
            FormFieldType.TEXT -> ValidationRule.create(ValidationRule.required())
            FormFieldType.NUMBER -> ValidationRule.create(ValidationRule.required())
            FormFieldType.PHONE -> ValidationRule.create(ValidationRule.required())
        }
        return FormState(initialValue, validation)
    }
}
