package bogusz.com.service.database.exceptions

sealed class FallDetectorException(override val message: String, val rationale: String = message) : Exception() {
    data class NoSuchRecordException(
        private val id: Long = 1L
    ) : FallDetectorException("Record with id $id don't exist in database", "Record don't exist in database.")

	object NoRecordsException : FallDetectorException("Didn't find any records in database.")

    object IceAlreadyExistsException : FallDetectorException("Ice contact already exists.")

    data class RecordNotUpdatedException(
        private val id: Long = 1L
    ) : FallDetectorException("Record with id $id was not updated", "Record was not updated.")

	override fun toString() = message
}