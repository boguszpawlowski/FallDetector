package com.example.bpawlowski.falldetector.screens.preference.model

import com.bpawlowski.domain.model.Sensitivity

interface EnumPreference {
    val label: String
    val values: List<String>
    val chosenValue: String
}

object SensitivityPreference : EnumPreference{
    override val label = Sensitivity::class.java.simpleName
    override val values = Sensitivity.values().toList().map { it.name }
    override val chosenValue = Sensitivity.LOW.name
}
