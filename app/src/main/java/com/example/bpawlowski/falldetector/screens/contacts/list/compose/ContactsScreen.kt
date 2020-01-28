package com.example.bpawlowski.falldetector.screens.contacts.list.compose

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.bpawlowski.domain.model.Contact
import com.example.bpawlowski.falldetector.R
import com.example.bpawlowski.falldetector.compose.FallDetectorTheme
import com.example.bpawlowski.falldetector.compose.Padding
import com.example.bpawlowski.falldetector.screens.contacts.list.ContactsViewState
import kotlinx.coroutines.flow.StateFlow
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactsScreen(
    viewState: StateFlow<ContactsViewState>,
    onItemClicked: (Contact) -> Unit,
    onCallClicked: (Contact) -> Unit,
    onTextMessageClicked: (Contact) -> Unit,
    onAddContactClicked: () -> Unit,
    onContactDismissed: (Contact) -> Unit
) {
    FallDetectorTheme {
        val state = viewState.collectAsState()

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {

            val (listContacts, buttonAddNew) = createRefs()

            LazyColumnFor(
                items = state.value.contacts,
                contentPadding = PaddingValues(Padding.Medium),
                modifier = Modifier.constrainAs(listContacts) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) { contact ->
                ContactItem(
                    contact = contact,
                    onItemClicked = onItemClicked,
                    onCallClicked = onCallClicked,
                    onTextMessageClicked = onTextMessageClicked,
                    onItemDismissed = onContactDismissed,
                    modifier = Modifier.fillParentMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Default.Add) },
                text = { Text(text = stringResource(id = R.string.fab_label).toUpperCase(Locale.getDefault())) },
                onClick = onAddContactClicked,
                modifier = Modifier.constrainAs(buttonAddNew) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }.padding(bottom = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun ContactScreenPreview() {

}
