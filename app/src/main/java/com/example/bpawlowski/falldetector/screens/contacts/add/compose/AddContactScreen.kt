package com.example.bpawlowski.falldetector.screens.contacts.add.compose

import android.util.Patterns
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.bpawlowski.domain.model.Contact
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.compose.FallDetectorTheme
import com.example.bpawlowski.falldetector.compose.Padding
import com.example.bpawlowski.falldetector.compose.Spacing
import com.example.bpawlowski.falldetector.screens.common.compose.ProfileBackground
import com.example.bpawlowski.falldetector.screens.common.compose.ProfilePicture
import com.example.bpawlowski.falldetector.screens.common.compose.ValidatedInputText
import java.util.*

@Composable
fun AddContactScreen(
    onContactAdded: (Contact) -> Unit,
    onCancel: () -> Unit
) {

    val (name, onNameChanged) = remember { mutableStateOf("") }
    val (mobile, onMobileChanged) = remember { mutableStateOf("") }
    val (email, onEmailChanged) = remember { mutableStateOf("") }
    val (ice, onIceChanged) = remember { mutableStateOf(false) }
    val (isNameValid, onNameValidChanged) = remember { mutableStateOf(false) }
    val (isMobileValid, onMobileValidChanged) = remember { mutableStateOf(false) }
    val (isEmailValid, onEmailValidChanged) = remember { mutableStateOf(false) }

    FallDetectorTheme {
        Column {
            Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                ProfileBackground()
                Text(
                    text = name.capitalize(Locale.getDefault()),
                    modifier = Modifier.align(Alignment.TopCenter),
                    style = MaterialTheme.typography.h3,
                    color = Color.White
                )
                ProfilePicture(
                    isIce = ice,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = Spacing.Large)
                )
                FloatingActionButton(
                    onClick = { onIceChanged(ice.not()) },
                    modifier = Modifier.align(Alignment.BottomCenter).padding(start = 150.dp)
                ) {
                    if (ice) {
                        Icon(asset = Icons.Default.Star)
                    } else {
                        Icon(asset = vectorResource(id = R.drawable.ic_star_border_black_24dp))
                    }
                }
            }
            ValidatedInputText(
                label = "Name",
                text = name,
                onTextChanged = onNameChanged,
                onValidChanged = onNameValidChanged,
                pattern = ".+",
                leadingIcon = { Icon(asset = Icons.Default.Person) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = Padding.Medium)
            )
            ValidatedInputText(
                label = "Mobile",
                text = mobile,
                onTextChanged = onMobileChanged,
                onValidChanged = onMobileValidChanged,
                pattern = "\\d{9}",
                leadingIcon = { Icon(asset = Icons.Default.Call) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = Padding.Medium)
            )
            ValidatedInputText(
                label = "Email",
                text = email,
                onTextChanged = onEmailChanged,
                onValidChanged = onEmailValidChanged,
                required = false,
                pattern = Patterns.EMAIL_ADDRESS,
                leadingIcon = { Icon(asset = Icons.Default.Email) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = Padding.Medium)
            )
            if (isNameValid && isEmailValid && isMobileValid) {
                Button(
                    onClick = {
                        onContactAdded(
                            Contact(
                                id = null,
                                name = name,
                                mobile = mobile.toInt(),
                                email = email,
                                isIce = ice,
                                photoPath = null
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth().padding(Padding.Small)
                ) {
                    Text(text = "Save")
                }
            }
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth().padding(Padding.Small)
            ) {
                Text(text = "Cancel")
            }
        }
    }
}

@Preview
@Composable
fun AddContactScreenPreview() {
    FallDetectorTheme {
        AddContactScreen({}, {})
    }
}
