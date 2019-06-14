package bogusz.com.service.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import bogusz.com.service.model.Contact

@Dao
internal interface ContactDao {

    @Query("SELECT * FROM contact")
    fun getAllData(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact WHERE id like :id")
    suspend fun getContactById(id: Long): Contact?

    @Query("SELECT * FROM contact")
    suspend fun getAll(): List<Contact>

    @Query("SELECT * FROM contact WHERE mobile LIKE :mobile")
    suspend fun getContactByMobile(mobile: Int): Contact?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact): Long

    @Delete
    suspend fun delete(contact: Contact): Int

    @Query(value = "UPDATE contact SET email=:email WHERE id like :id")
    suspend fun updateEmail(id: Long, email: String): Int

    @Query(value = "SELECT id FROM contact WHERE user_priority = 1")
    suspend fun findIceContact(): Long?
}