package com.newAndroid.newandroidjetpackcompose.presentation.ui.forms

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormField(
    formState: FormState<String>,
    label: String,
    leadingIcon: ImageVector? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val shakeOffset = remember { Animatable(0f) }
    var previousError by remember { mutableStateOf(formState.error) }

    LaunchedEffect(formState.error) {
        if (formState.error != null && previousError == null) {
            repeat(8) {
                shakeOffset.animateTo(5f, tween(10))
                shakeOffset.animateTo(-5f, tween(10))
            }
            shakeOffset.animateTo(0f, tween(10))
        }
        previousError = formState.error
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = formState.value,
            onValueChange = formState::updateValue,
            label = {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            leadingIcon = leadingIcon?.let { icon ->
                {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(
                            text = if (passwordVisible) "👁️" else "👁️‍🗨️",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else null,
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = shakeOffset.value.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            singleLine = true,
            enabled = enabled,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            isError = formState.error != null
        )

        if (formState.error != null) {
            Text(
                text = formState.error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}

@Composable
fun FormFieldByType(
    formState: FormState<String>,
    type: FormFieldType,
    label: String? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val (leadingIcon, isPassword, keyboardType) = when (type) {
        FormFieldType.EMAIL -> Triple(Icons.Default.Email, false, KeyboardType.Email)
        FormFieldType.PASSWORD -> Triple(Icons.Default.Lock, true, KeyboardType.Password)
        FormFieldType.TEXT -> Triple(null, false, KeyboardType.Text)
        FormFieldType.NUMBER -> Triple(null, false, KeyboardType.Number)
        FormFieldType.PHONE -> Triple(null, false, KeyboardType.Phone)
    }

    FormField(
        formState = formState,
        label = label ?: type.name.lowercase().replaceFirstChar { it.uppercase() },
        leadingIcon = leadingIcon,
        isPassword = isPassword,
        keyboardType = keyboardType,
        modifier = modifier,
        enabled = enabled
    )
}


