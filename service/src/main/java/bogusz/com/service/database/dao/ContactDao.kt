package bogusz.com.service.database.dao

import androidx.room.*
import bogusz.com.service.model.Contact
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
internal interface ContactDao {

    @Query("SELECT * FROM contact")
    fun getAll(): Flowable<List<Contact>>

    @Query("SELECT * FROM contact")
    fun fetchAll(): Single<List<Contact>>

    @Query("SELECT * FROM contact WHERE mobile LIKE :mobile")
    fun getContactByMobile(mobile: Int): Single<Contact>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(contact: Contact): Single<Long>

    @Update
    fun update(contact: Contact): Completable

    @Delete
    fun delete(contact: Contact): Completable

    @Query(value = "UPDATE contact SET email=:email WHERE id like :id")
    fun updateEmail(id: Long, email: String): Completable

    @Query(value = "SELECT id FROM contact WHERE user_priority = 1")
    fun findIceContact(): Single<Long>
}