package com.example.bpawlowski.falldetector.service.model

data class Contact(
    val name: String,
    val mobile: Int,
    val email: String?,
    val priority: UserPriority = UserPriority.PRIORITY_NORMAL
)