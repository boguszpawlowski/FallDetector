package bogusz.com.service.database.exceptions

sealed class FallDetectorException(override val message: String) : Exception() {
    object NoSuchRecordException : FallDetectorException("Record don't exist in database.")
    object NoRecordsException : FallDetectorException("Didn't fin any records in database.")
    object IceAlreadyExistsException : FallDetectorException("Ice contact already exists.")
    object RecordNotUpdatedException : FallDetectorException("Record was not updated.")
}