package bogusz.com.service.database.exceptions

sealed class DatabaseException: Throwable(){
    object DuplicateRowException: DatabaseException()
}