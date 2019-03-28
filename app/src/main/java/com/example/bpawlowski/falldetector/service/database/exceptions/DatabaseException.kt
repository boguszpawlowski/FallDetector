package com.example.bpawlowski.falldetector.service.database.exceptions

sealed class DatabaseException: Throwable(){
    object DuplicateRowException: DatabaseException()
}