package com.bpawlowski.core.exception

sealed class FallDetectorException(override val message: String, val rationale: String = message) : Exception() {
	data class NoSuchRecordException(
		private val id: Long?
	) : FallDetectorException("Record with id $id don't exist in database", "Record don't exist in database.")

	data class InvalidMobileException(
		private val mobile: Int?
	) : FallDetectorException("Record with mobile $mobile don't exist in database")

	object NoRecordsException : FallDetectorException("Didn't find any records in database.")

	object IceAlreadyExistsException : FallDetectorException("Ice contact already exists.")

	data class RecordNotUpdatedException(
		private val id: Long?
	) : FallDetectorException("Record with id $id was not updated", "Record was not updated.")

	data class MobileAlreadyExisting(
		private val mobile: Int
	) : FallDetectorException("Record with mobile $mobile already existing.")

	object StateNotInitializedException : FallDetectorException("Service state is not initialized")

	override fun toString() = message
}