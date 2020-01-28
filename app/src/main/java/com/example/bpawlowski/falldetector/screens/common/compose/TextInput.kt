package com.example.bpawlowski.falldetector.screens.common.compose

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.regex.Pattern

@Composable
fun ValidatedInputText(
    text: String = "",
    onTextChanged: (String) -> Unit,
    onValidChanged: (Boolean) -> Unit = {},
    label: String,
    pattern: String = ".*",
    required: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    ValidatedInputText(
        text = text,
        required = required,
        label = label,
        onTextChanged = onTextChanged,
        onValidChanged = onValidChanged,
        pattern = Pattern.compile(pattern),
        modifier = modifier,
        leadingIcon = leadingIcon
    )
}

@Composable
fun ValidatedInputText(
    text: String = "",
    onTextChanged: (String) -> Unit,
    onValidChanged: (Boolean) -> Unit = {},
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    pattern: Pattern,
    required: Boolean = true,
    modifier: Modifier = Modifier
) {
    val validation = remember { pattern.toRegex() }
    val isError = if (required) {
        validation.matches(text).not()
    } else {
        validation.matches(text).not() && text.isNotEmpty()
    }
    onValidChanged.invoke(isError.not())
    OutlinedTextField(
        modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp),
        value = text,
        label = { Text(text = label) },
        isErrorValue = isError,
        onValueChange = onTextChanged,
        leadingIcon = leadingIcon,
    )
}
