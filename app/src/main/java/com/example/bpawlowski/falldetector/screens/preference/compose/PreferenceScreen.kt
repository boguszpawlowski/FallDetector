package com.example.bpawlowski.falldetector.screens.preference.compose

import androidx.compose.animation.animate
import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.bpawlowski.domain.model.Sensitivity
import com.example.bpawlowski.falldetector.compose.FallDetectorTheme
import com.example.bpawlowski.falldetector.compose.Padding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import java.util.*

@Composable
fun PreferenceScreen(
    preferenceFlow: StateFlow<List<PreferenceItem>>,
    onBooleanPreferenceChanged: (String, Boolean) -> Unit,
    onListPreferenceChanged: (String, String) -> Unit
) {
    val preferences = preferenceFlow.collectAsState()
    FallDetectorTheme {
        ScrollableColumn(
            verticalArrangement = Arrangement.spacedBy(Padding.Small),
            contentPadding = PaddingValues(top = Padding.Small, bottom = Padding.Small)
        ) {
            preferences.value.forEach { preference ->
                when (preference) {
                    is PreferenceItem.BooleanPreference -> {
                        BooleanPreferenceItem(
                            key = preference.label,
                            value = preference.value,
                            onCheckedChange = onBooleanPreferenceChanged
                        )
                    }
                    is PreferenceItem.ListPreference -> {
                        ListPreference(
                            key = preference.label,
                            values = preference.values,
                            onListPreferenceChanged = onListPreferenceChanged
                        )
                    }
                    is PreferenceItem.Header -> Header(key = preference.label)
                }
            }
        }
    }
}

@Composable
fun BooleanPreferenceItem(key: String, value: Boolean, onCheckedChange: (String, Boolean) -> Unit) {
    Card(
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = Padding.Medium)
            .clickable(onClick = {})
    ) {
        ConstraintLayout {
            val (label, switch) = createRefs()

            Text(
                text = key.capitalize(Locale.getDefault()),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.constrainAs(label) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }.padding(start = Padding.Medium)
            )
            Switch(
                checked = value,
                onCheckedChange = { onCheckedChange(key, value.not()) },
                modifier = Modifier.constrainAs(switch) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }.padding(end = Padding.Medium)
            )
        }
    }
}

@Composable
fun ListPreference(
    key: String,
    values: List<PreferenceOption>,
    onListPreferenceChanged: (String, String) -> Unit
) {
    val (expand, onExpandChanged) = remember { mutableStateOf(false) }

    val height = if (expand) 200.dp else 60.dp

    Card(
        elevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(animate(target = height))
            .padding(horizontal = Padding.Medium)
            .clickable(onClick = { })
    ) {
        ConstraintLayout {

            val (text, button, list) = createRefs()

            Text(
                text = key.capitalize(Locale.getDefault()),
                style = MaterialTheme.typography.h6,
                modifier = Modifier.constrainAs(text) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }.padding(start = Padding.Medium, top = Padding.Medium),
            )

            IconButton(
                onClick = { onExpandChanged(expand.not()) },
                modifier = Modifier.constrainAs(button) {
                    end.linkTo(parent.end)
                    top.linkTo(text.top)
                }.padding(top = Padding.Small)
            ) {
                Icon(asset = if (expand) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown)
            }

            if (expand) {
                Column(
                    modifier = Modifier.constrainAs(list) {
                        top.linkTo(text.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                    }.fillMaxWidth()
                ) {
                    values.forEach {
                        ListItem(modifier = Modifier.clickable(
                            onClick = {
                                onListPreferenceChanged(
                                    Sensitivity::class.java.simpleName,
                                    it.label
                                )
                            }
                        )) {
                            Row {
                                Text(text = it.label)
                                if (it.chosen) {
                                    RadioButton(selected = true, onClick = {})
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Header(key: String) {
    Text(
        text = key.capitalize(Locale.getDefault()),
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.secondary,
        modifier = Modifier.padding(start = Padding.Medium)
    )
}

@Preview
@Composable
fun PreferencePreview() {
    PreferenceScreen(
        preferenceFlow = MutableStateFlow(
            listOf(
                PreferenceItem.Header("Visuals"),
                PreferenceItem.BooleanPreference("Dark mode", true),
                PreferenceItem.BooleanPreference("Send location", false),
                PreferenceItem.Header("Functional"),
                PreferenceItem.BooleanPreference("Send SMS", false),
                PreferenceItem.ListPreference(
                    Sensitivity::class.java.simpleName,
                    Sensitivity.values().toList()
                        .map { PreferenceOption(it.name, it.name == "LOW") }
                )
            )
        ),
        { _, _ -> },
        { _, _ -> }
    )
}

sealed class PreferenceItem {
    data class BooleanPreference(val label: String, val value: Boolean) : PreferenceItem()

    data class ListPreference(val label: String, val values: List<PreferenceOption>) :
        PreferenceItem()

    data class Header(val label: String) : PreferenceItem()
}

data class PreferenceOption(val label: String, val chosen: Boolean)
