package bogusz.com.service.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "service_state")
data class ServiceState(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    @NonNull
    var id: Long? = null,

    @ColumnInfo(name = "is_running", typeAffinity = ColumnInfo.INTEGER)
    @NonNull
    var isRunning: Boolean,

    @ColumnInfo(name = "sensitivity")
    @NonNull
    var sensitivity: Sensitivity
){
    @Ignore
    constructor(): this(null, false, Sensitivity.HIGH)
}