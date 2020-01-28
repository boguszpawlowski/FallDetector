package com.example.bpawlowski.falldetector.screens.contacts.details.compose

import android.util.Patterns
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    contact: Contact,
    onContactEdited: (Contact) -> Unit,
    onCallClicked: () -> Unit,
    onEmailClicked: () -> Unit,
    onTextMessageClicked: () -> Unit,
) {
    val (name, onNameChanged) = remember { mutableStateOf(contact.name) }
    val (mobile, onMobileChanged) = remember { mutableStateOf(contact.mobile.toString()) }
    val (email, onEmailChanged) = remember { mutableStateOf(contact.email.orEmpty()) }
    val (ice, onIceChanged) = remember { mutableStateOf(contact.isIce) }

    fun reloadState() {
        onNameChanged.invoke(contact.name)
        onEmailChanged.invoke(contact.email.orEmpty())
        onMobileChanged.invoke(contact.mobile.toString())
        onIceChanged.invoke(contact.isIce)
    }

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
            Spacer(modifier = Modifier.height(Spacing.Small))
            Row(Modifier.align(Alignment.CenterHorizontally)) {
                ProfileActionButton(
                    icon = Icons.Default.Call,
                    onClick = onCallClicked
                )
                Spacer(modifier = Modifier.width(Spacing.Medium))
                ProfileActionButton(
                    icon = Icons.Default.Email,
                    onClick = onEmailClicked
                )
                Spacer(modifier = Modifier.width(Spacing.Medium))
                ProfileActionButton(
                    icon = Icons.Default.Sms,
                    onClick = onTextMessageClicked
                )
            }
            ValidatedInputText(
                label = "Mobile",
                text = mobile,
                onTextChanged = onMobileChanged,
                pattern = "\\d{9}",
                leadingIcon = { Icon(asset = Icons.Default.Call) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = Padding.Medium)
            )
            ValidatedInputText(
                label = "Email",
                text = email,
                onTextChanged = onEmailChanged,
                required = false,
                pattern = Patterns.EMAIL_ADDRESS,
                leadingIcon = { Icon(asset = Icons.Default.Email) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = Padding.Medium)
            )

            if (diffForm(contact, name, email, mobile, ice)) {
                ContactActionButtons(
                    onSave = {
                        onContactEdited(
                            contact.copy(
                                name = name,
                                email = email,
                                mobile = mobile.toInt(),
                                isIce = ice
                            )
                        )
                    },
                    onReset = { reloadState() },
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileActionButton(icon: VectorAsset, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(Spacing.Medium)
            .border(
                width = Dp.Hairline,
                color = MaterialTheme.colors.onSurface,
                shape = CircleShape
            )
    ) {
        Icon(asset = icon, modifier = Modifier.size(32.dp), tint = MaterialTheme.colors.onSurface)
    }
}

@Composable
fun ContactActionButtons(
    onSave: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save")
        }
        OutlinedButton(
            onClick = onReset,
            modifier = Modifier.fillMaxWidth().padding(top = Padding.Small)
        ) {
            Text(text = "Reset")
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        Surface {
            ProfileScreen(
                Contact(
                    name = "lol",
                    email = null,
                    mobile = 123123123,
                    isIce = false
                ), {}, {}, {}, {}
            )
        }
    }
}

@Preview
@Composable
fun ProfileActionButtonPreview() {
    MaterialTheme {
        ProfileActionButton(icon = vectorResource(id = R.drawable.ic_call_black_24dp), onClick = {})
    }
}
