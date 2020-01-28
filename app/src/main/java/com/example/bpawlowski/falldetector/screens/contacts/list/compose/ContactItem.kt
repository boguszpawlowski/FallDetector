package com.example.bpawlowski.falldetector.screens.contacts.list.compose

import androidx.compose.animation.animate
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.*
import androidx.compose.material.DismissValue.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Sms
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.bpawlowski.domain.model.Contact
import com.example.bpawlowski.falldetector.compose.FallDetectorTheme
import com.example.bpawlowski.falldetector.compose.Padding
import dev.chrisbanes.accompanist.coil.CoilImage
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactItem(
    contact: Contact,
    onItemClicked: (Contact) -> Unit,
    onCallClicked: (Contact) -> Unit,
    onTextMessageClicked: (Contact) -> Unit,
    onItemDismissed: (Contact) -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it != Default) onItemDismissed(contact)
            it == Default
        }
    )

    SwipeableItem(dismissState = dismissState) {
        Card(
            elevation = animate(if (dismissState.dismissDirection != null) 8.dp else 2.dp),
            modifier = modifier.clickable(onClick = { onItemClicked(contact) }),
        ) {
            ConstraintLayout {
                val (photo, textName, textMobile, textEmail, buttonCall, buttonTextMessage) = createRefs()

                CoilImage(
                    data = "https://picsum.photos/300/300",
                    modifier = Modifier
                        .constrainAs(photo) {
                            start.linkTo(parent.start)
                        }
                        .padding(Padding.Small)
                        .border(
                            BorderStroke(Dp.Hairline, MaterialTheme.colors.primary),
                            shape = CircleShape
                        )
                        .padding(Dp.Hairline)
                        .clip(CircleShape)
                        .size(70.dp),
                    fadeIn = true,
                    contentScale = ContentScale.Crop,
                    loading = { CircularProgressIndicator(modifier = Modifier.size(30.dp)) }
                )
                ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.high) {
                    Text(
                        text = contact.name.capitalize(Locale.getDefault()),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.constrainAs(textName) {
                            top.linkTo(parent.top)
                            start.linkTo(photo.end)
                        }
                    )
                }
                ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
                    Text(
                        text = contact.mobile.toString(),
                        modifier = Modifier.constrainAs(textMobile) {
                            top.linkTo(textName.bottom)
                            start.linkTo(textName.start)
                        }
                    )
                    Text(
                        text = contact.email.orEmpty(),
                        modifier = Modifier.constrainAs(textEmail) {
                            top.linkTo(textMobile.bottom)
                            start.linkTo(textName.start)
                        })
                }

                IconButton(
                    onClick = { onCallClicked(contact) },
                    modifier = Modifier.constrainAs(buttonCall) {
                        end.linkTo(buttonTextMessage.start)
                        top.linkTo(buttonTextMessage.top)
                        bottom.linkTo(buttonTextMessage.bottom)
                    }
                ) {
                    Icon(asset = Icons.Default.Call, modifier = Modifier.size(24.dp))
                }
                IconButton(
                    onClick = { onTextMessageClicked(contact) },
                    modifier = Modifier.constrainAs(buttonTextMessage) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    Icon(asset = Icons.Default.Sms, modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableItem(
    dismissState: DismissState,
    content: @Composable RowScope.() -> Unit
) {
    SwipeToDismiss(
        state = dismissState,
        directions = setOf(StartToEnd, EndToStart),
        dismissThresholds = { direction ->
            FractionalThreshold(if (direction == StartToEnd) 0.25f else 0.5f)
        },
        background = {},
        dismissContent = content
    )
}

@Preview
@Composable
fun ContactItemPreview() {
    FallDetectorTheme {
        ContactItem(
            contact = Contact(
                id = null,
                name = "Tomasz",
                mobile = 123123123,
                email = "tomasz@example.com",
                photoPath = null,
                isIce = true
            ),
            onItemClicked = {},
            onCallClicked = {},
            onTextMessageClicked = {},
            onItemDismissed = {}
        )
    }
}
