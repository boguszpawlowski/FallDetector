package com.bpawlowski.domain.model

data class Contact(
    val id: Long? = null,
    val name: String,
    val mobile: Int,
    val email: String?,
    val isIce: Boolean,
    val photoPath: String? = null
)
