package com.bpawlowski.domain.util

import org.joda.time.DateTime

val doNothing = Unit

fun String.dayMonthDate(): String =
    DateTime.parse(this).toString("dd MMMM")

fun String.dayMonthHourDate(): String =
    DateTime.parse(this).toString("dd MMMM HH:mm (EEE)")
