package com.example.bpawlowski.falldetector.screens.contacts.details.compose

import com.bpawlowski.domain.model.Contact

fun diffForm(initial: Contact, newName: String, newEmail: String, newMobile: String, ice: Boolean): Boolean =
    initial.name != newName || initial.email != newEmail || initial.mobile.toString() != newMobile || ice != initial.isIce
