package bogusz.com.service.database.dao

import androidx.room.*
import bogusz.com.service.model.Contact
import bogusz.com.service.model.UserPriority
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact")
    fun getAll(): Flowable<List<Contact>>

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

    @Query(value = "SELECT id FROM contact WHERE user_priority = :priority")
    fun findIceContact(priority: UserPriority? = UserPriority.PRIORITY_ICE): Single<Long>
}