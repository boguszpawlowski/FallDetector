package com.bpawlowski.domain.model

data class Event(
    val id: Long? = null,
    val title: String,
    val creatorId: Long,
    val latLang: Pair<Double, Double>,
    val date: String,
    val remoteId: Long?,
    val attendingUsers: Int,
    val isAttending: Boolean,
    val creatorMobile: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

        if (title != other.title) return false
        if (creatorId != other.creatorId) return false
// 		if (latLang != other.latLang) return false //todo add this
        if (date != other.date) return false
        if (remoteId != other.remoteId) return false
        if (attendingUsers != other.attendingUsers) return false
        if (creatorMobile != other.creatorMobile) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + creatorId.hashCode()
// 		result = 31 * result + latLang.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + (remoteId?.hashCode() ?: 0)
        result = 31 * result + attendingUsers
        result = 31 * result + creatorMobile.hashCode()
        return result
    }
}
