package com.bpawlowski.core.model

data class Contact(
	val id: Long? = null,
	val name: String,
	val mobile: Int,
	val email: String?,
	val priority: ContactPriority,
	val photoPath: String? = null
)