package com.bpawlowski.database.datasource

import com.bpawlowski.domain.model.Contact
import com.bpawlowski.data.datasource.ContactLocalDataSource
import com.bpawlowski.database.dao.ContactDao
import com.bpawlowski.database.entity.toDomain
import com.bpawlowski.database.entity.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactLocalDataSourceImpl(
    private val contactDao: ContactDao
) : ContactLocalDataSource {

    override fun getAllFlow(): Flow<List<Contact>> =
        contactDao.getAllFlow().map { it.map { it.toDomain() } }

    override suspend fun getAll(): List<Contact> =
        contactDao.getAll().map { it.toDomain() }

    override suspend fun insert(contact: Contact): Long =
        contactDao.insert(contact.toEntity())

    override suspend fun getById(contactId: Long): Contact? =
        contactDao.getContactById(contactId)?.toDomain()

    override suspend fun update(contact: Contact): Int =
        contactDao.update(contact.toEntity())

    override suspend fun updateEmail(contactId: Long, email: String): Int =
        contactDao.updateEmail(contactId, email)

    override suspend fun updatePhotoPath(contactId: Long, photoPath: String): Int =
        contactDao.updatePhotoPath(contactId, photoPath)

    override suspend fun delete(contact: Contact): Int =
        contactDao.delete(contact.toEntity())

    override suspend fun findIceContact(): Contact? =
        contactDao.findIceContact()?.toDomain()

    override suspend fun getContactByMobile(mobile: Int): Contact? =
        contactDao.getContactByMobile(mobile)?.toDomain()
}
