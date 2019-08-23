package com.bpawlowski.database.contact

import com.bpawlowski.core.exception.FallDetectorException
import com.bpawlowski.core.model.ContactPriority
import com.bpawlowski.database.dao.ContactDao
import com.bpawlowski.database.dbservice.DatabaseService
import com.bpawlowski.database.entity.ContactDb
import com.bpawlowski.database.repository.ContactRepositoryImpl
import com.bpawlowski.data.toDomain
import com.bpawlowski.data.toEntity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ContactRepositoryTest {

	@Mock
	private lateinit var databaseService: DatabaseService

	@Mock
	private lateinit var contactDao: ContactDao

	private lateinit var contactRepository: ContactRepositoryImpl

	private val iceContact = getDummyContact(ID, MOBILE, ContactPriority.PRIORITY_ICE)
	private val normalContact = getDummyContact(ID_2, DIFFERENT_MOBILE)
	private val duplicateMobileContact = getDummyContact(null, DIFFERENT_MOBILE).toDomain()
	private val newIceContact = getDummyContact(null, DIFFERENT_MOBILE, ContactPriority.PRIORITY_ICE).toDomain()
	private val newValidContact = getDummyContact(null, DIFFERENT_MOBILE).toDomain()

	@Before
	fun init() {
		MockitoAnnotations.initMocks(this)

		runBlocking {
			whenever(databaseService.getContactDao()) doReturn contactDao

			contactRepository = ContactRepositoryImpl(databaseService)
		}
	}

	@Test
	fun `When there is contact with same mobile then return failure`() = runBlocking {
		whenever(contactDao.getContactByMobile(normalContact.mobile)) doReturn normalContact

		val result = contactRepository.addContact(duplicateMobileContact)

		verify(contactDao).getContactByMobile(duplicateMobileContact.mobile)
		verify(contactDao, never()).insert(any())
		assert((result as? com.bpawlowski.core.domain.FallDetectorResult.Result.Failure)?.error == FallDetectorException.MobileAlreadyExisting(normalContact.mobile))
	}

	@Test
	fun `When ice contact exists then don't allow to add another`() = runBlocking {
		whenever(contactDao.findIceContact()) doReturn iceContact.id

		val result = contactRepository.addContact(newIceContact)

		verify(contactDao, never()).insert(any())
		assert((result as? com.bpawlowski.core.domain.FallDetectorResult.Result.Failure)?.error == FallDetectorException.IceAlreadyExistsException)
	}

	@Test
	fun `When contact is valid, add it to database`() = runBlocking {
		whenever(contactDao.findIceContact()) doReturn iceContact.id
		whenever(contactDao.getContactByMobile(newIceContact.mobile)) doReturn null
		whenever(contactDao.insert(eq(newValidContact.toEntity()))) doReturn 1L

		val result = contactRepository.addContact(newValidContact)

		verify(contactDao).insert(newValidContact.toEntity())
		assert((result as? com.bpawlowski.core.domain.FallDetectorResult.Result.Success)?.data == 1L)
	}

	@Test
	fun `When iceContact has same id then allow to update it`() = runBlocking {
		whenever(contactDao.findIceContact()) doReturn iceContact.id
		whenever(contactDao.update(eq(iceContact.copy(name = NEW_NAME)))) doReturn 1

		val result = contactRepository.updateContact(iceContact.copy(name = NEW_NAME).toDomain())

		verify(contactDao).update(iceContact.copy(name = NEW_NAME))
		assert((result as? com.bpawlowski.core.domain.FallDetectorResult.Result.Success)?.data == Unit)
	}

	private fun getDummyContact(id: Long?, mobile: Int, priority: ContactPriority = ContactPriority.PRIORITY_NORMAL) =
		ContactDb(
			id = id,
			name = NAME,
			mobile = mobile,
			email = EMAIL,
			priority = priority,
			photoPath = PHOTO_PATH
		)

	companion object {
		private const val ID = 123L
		private const val ID_2 = 124L
		private const val MOBILE = 123
		private const val DIFFERENT_MOBILE = 1234
		private const val NAME = "name"
		private const val NEW_NAME = "new_name"
		private const val EMAIL = "email@gmail.com"
		private const val PHOTO_PATH = "storage/some_url"
	}
}