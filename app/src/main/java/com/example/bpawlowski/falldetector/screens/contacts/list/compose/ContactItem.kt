package com.example.bpawlowski.falldetector.screens.contacts.list.compose

import androidx.compose.animation.animate
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.*
import androidx.compose.material.DismissValue.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Sms
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.bpawlowski.domain.model.Contact
import com.example.bpawlowski.falldetector.compose.Corners
import com.example.bpawlowski.falldetector.compose.FallDetectorTheme
import com.example.bpawlowski.falldetector.compose.Medium
import com.example.bpawlowski.falldetector.compose.Padding
import com.example.bpawlowski.falldetector.compose.button.CircleActionButton
import com.example.bpawlowski.falldetector.compose.button.PrimaryButton
import dev.chrisbanes.accompanist.coil.CoilImage
import java.util.*

// TODO add ICE item and normal item
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
        if (contact.isIce) {
            IceItem(
                contact = contact,
                onItemClicked = onItemClicked,
                onTextMessageClicked = onTextMessageClicked,
                onCallClicked = onCallClicked,
                modifier = modifier
            )
        } else {
            NormalItem(
                contact = contact,
                onItemClicked = onItemClicked,
                onTextMessageClicked = onTextMessageClicked,
                onCallClicked = onCallClicked,
                modifier = modifier
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NormalItem(
    contact: Contact,
    elevation: Dp = 2.dp,
    modifier: Modifier = Modifier,
    onItemClicked: (Contact) -> Unit,
    onTextMessageClicked: (Contact) -> Unit,
    onCallClicked: (Contact) -> Unit
) {
    Card(
        backgroundColor = Color.White,
        contentColor = Color.DarkGray,
        elevation = animate(elevation),
        shape = RoundedCornerShape(Corners.Medium),
        modifier = modifier,
    ) {
        ConstraintLayout(
            modifier = Modifier.clickable(onClick = { onItemClicked(contact) })
                .padding(Padding.Medium)
        ) {
            val (textName, buttonCall, buttonTextMessage) = createRefs()

            ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.high) {
                Text(
                    text = contact.name.capitalize(Locale.getDefault()),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.constrainAs(textName) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                )
            }

            CircleActionButton(
                icon = Icons.Default.Call,
                onClick = { onCallClicked(contact) },
                modifier = Modifier.constrainAs(buttonCall) {
                    end.linkTo(buttonTextMessage.start, margin = Padding.Small)
                    top.linkTo(buttonTextMessage.top)
                    bottom.linkTo(buttonTextMessage.bottom)
                }
            )

            CircleActionButton(
                icon = Icons.Default.Sms,
                onClick = { onTextMessageClicked(contact) },
                modifier = Modifier.constrainAs(buttonTextMessage) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IceItem(
    contact: Contact,
    elevation: Dp = 2.dp,
    modifier: Modifier = Modifier,
    onItemClicked: (Contact) -> Unit,
    onTextMessageClicked: (Contact) -> Unit,
    onCallClicked: (Contact) -> Unit
) {
    Column {
        Card(
            backgroundColor = Color.White,
            contentColor = Color.DarkGray,
            elevation = animate(elevation),
            modifier = modifier,
        ) {
            ConstraintLayout(
                modifier = Modifier.clickable(onClick = { onItemClicked(contact) })
                    .padding(Padding.Medium)
            ) {
                val (photo, textName, textIce, textMobile, textEmail) = createRefs()

                CoilImage(
                    data = "https://picsum.photos/300/300",
                    modifier = Modifier
                        .constrainAs(photo) {
                            start.linkTo(parent.start)
                        }
                        .border(
                            BorderStroke(Dp.Hairline, MaterialTheme.colors.primary),
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(Dp.Hairline)
                        .clip(MaterialTheme.shapes.small)
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
                            start.linkTo(photo.end, margin = Padding.Small)
                        }
                    )
                    Text(
                        text = "(ICE)",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.constrainAs(textIce) {
                            top.linkTo(textName.top)
                            bottom.linkTo(textName.bottom)
                            start.linkTo(textName.end, margin = Padding.ExtraSmall)
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
            }
        }
        ConstraintLayout(modifier = Modifier.padding(top = Padding.Medium).fillMaxWidth()) {
            val (buttonCall, buttonMessage) = createRefs()

            PrimaryButton(text = "Call", modifier = Modifier.constrainAs(buttonCall) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }) { onCallClicked(contact) }
            PrimaryButton(
                text = "Message",
                modifier = Modifier.constrainAs(buttonMessage) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
                backgroundColor = Color.DarkGray,
                textColor = Color.White
            ) { onTextMessageClicked(contact) }
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
fun ICEContactItemPreview() {
    FallDetectorTheme(darkTheme = true) {
        IceItem(
            contact = Contact(
                id = null,
                name = "Lorem",
                mobile = 123123123,
                email = "lorem@ipsum.com",
                photoPath = null,
                isIce = true
            ),
            onItemClicked = {},
            onCallClicked = {},
            onTextMessageClicked = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun NormalContactItemPreview() {
    FallDetectorTheme(darkTheme = true) {
        NormalItem(
            contact = Contact(
                id = null,
                name = "Lorem",
                mobile = 123123123,
                email = "lorem@ipsum.com",
                photoPath = null,
                isIce = false
            ),
            onItemClicked = {},
            onCallClicked = {},
            onTextMessageClicked = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
