package com.example.bpawlowski.falldetector.screens.common.compose

import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.dp

enum class ExpandableItemState {
    COLLAPSED, EXPANDED
}

val height = DpPropKey()

val expandTransitionDefinition = transitionDefinition<ExpandableItemState> {
    state(ExpandableItemState.COLLAPSED) {
        this[height] = 60.dp
    }

    state(ExpandableItemState.EXPANDED) {
        this[height] = 300.dp
    }

    transition(fromState = ExpandableItemState.EXPANDED, toState = ExpandableItemState.COLLAPSED) {
        height using tween(durationMillis = 250)
    }

    transition(fromState = ExpandableItemState.COLLAPSED, toState = ExpandableItemState.EXPANDED) {
        height using tween(durationMillis = 250)
    }
}
