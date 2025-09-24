package com.newAndroid.newandroidjetpackcompose.presentation.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newAndroid.newandroidjetpackcompose.presentation.constants.Routes
import com.newAndroid.newandroidjetpackcompose.presentation.navigation.AppNavigator
import com.newAndroid.newandroidjetpackcompose.presentation.ui.forms.FormBuilder
import com.newAndroid.newandroidjetpackcompose.presentation.ui.forms.ValidationRule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
) : ViewModel() {

    val form = FormBuilder()
        .addField(
            "email", "user@gmail.com",
            validation = ValidationRule.create(
                ValidationRule.required("Email is required"),
                ValidationRule.email("Invalid email format")
            ),
        )
        .addField(
            "password", "password",
            validation = ValidationRule.create(
                ValidationRule.required("Password is required"),
                ValidationRule.password(6, "Password must be at least 6 characters")
            ),
        )

    val emailFormState get() = form.getField("email")!!
    val passwordFormState get() = form.getField("password")!!

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    val isFormValid: Boolean get() = form.isValid()

    fun login() {
        _error.value = null

        form.forceValidateAll()

        if (!form.isValid()) {
            return
        }

        viewModelScope.launch {
            _isLoading.value = true

            try {
                kotlinx.coroutines.delay(1500)

                val values = form.getValues()
                val email = values["email"] ?: ""
                val password = values["password"] ?: ""

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    appNavigator.push(Routes.PAGE_ONE)
                } else {
                    _error.value = "Invalid login credentials"
                }
            } catch (e: Exception) {
                _error.value = "An error occurred: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetForm() {
        form.resetAll()
        _error.value = null
    }
}
