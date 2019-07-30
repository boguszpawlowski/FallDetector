package bogusz.com.service.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

	@Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(contact: Contact): Long

    @Delete
    suspend fun delete(contact: Contact): Int

	@Update
	suspend fun update(contact: Contact): Int

    @Query(value = "UPDATE contact SET email=:email WHERE id like :id")
    suspend fun updateEmail(id: Long, email: String): Int

	@Query(value = "UPDATE contact SET photo_path=:photoPath WHERE id like :id")
	suspend fun updatePhotoPath(id: Long, photoPath: String): Int

    @Query(value = "SELECT id FROM contact WHERE user_priority = 1")
    suspend fun findIceContact(): Long?
}