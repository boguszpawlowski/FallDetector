package bogusz.com.service.database.exceptions

sealed class FallDetectorException(override val message: String) : Exception() {
    object NoSuchContactException : FallDetectorException("Contact don't exist in database.")
    object NoContactsException : FallDetectorException("Didn't fin any contacts in database.")
    object IceAlreadyExistsException : FallDetectorException("Ice contact already exists.")
}